package shared.com.ortb.demadSidePlatforms.traits.getters

import shared.com.ortb.demadSidePlatforms.DemandSidePlatformBiddingAgent
import shared.com.ortb.enumeration.DatabaseActionType
import shared.com.ortb.model.auctionbid.biddingRequests.ImpressionModel
import shared.com.ortb.model.auctionbid.{ ImpressionBiddableAttributesModel, ImpressionBiddableInfoModel }
import shared.com.ortb.model.logging.LogTraceModel
import shared.com.ortb.model.results.DemandSidePlatformBiddingRequestWrapperModel
import shared.com.ortb.persistent.repositories.AdvertiseRepository
import shared.com.ortb.persistent.schema.Tables
import shared.com.repository.traits.FutureToRegular
import shared.io.helpers.EmptyValidateHelper
import slick.jdbc.SQLiteProfile.api._
import slick.lifted.TableQuery

import scala.concurrent.Future

trait BiddableInfoModelsGetter {
  this : DemandSidePlatformBiddingAgent =>

  def getImpressionBiddableInfo(
    advertisesTable : TableQuery[Tables.Advertise],
    advertiseRepository : AdvertiseRepository,
    impression : ImpressionModel,
    limit : Int = defaultAdvertiseLimit) : Option[ImpressionBiddableInfoModel] = {
    val methodName = "getImpressionBiddableInfoModel"

    val isEmptyBannerAndVideo = EmptyValidateHelper.isEmpty(impression.banner) &&
      EmptyValidateHelper.isEmpty(impression.video)

    if (isEmptyBannerAndVideo) {
      return EmptyImpressionBiddableInfoFor(impression)
    }

    val maybeBanner = impression.banner
    val maybeVideo = impression.video
    val advertisesQueryIn = advertisesTable.filter(advertise =>
      advertise.isvideo === 0)

    val advertisesBannerQuery = appendQueryForBanner(advertisesQueryIn, maybeBanner)
    val advertisesQuery = appendQueryForVideo(advertisesBannerQuery, maybeVideo)
    val countQuery = advertisesQuery.length.result
    val query = advertisesQuery.take(limit).result
    val exactQueryRows = runQuery(advertisesQuery)
    val totalCount = advertiseRepository.count(countQuery)
    val rows = advertiseRepository.run(query)
    val isBiddable = rows.nonEmpty

    val impressionAttributes = ImpressionBiddableAttributesModel(
      hasBanner = maybeBanner.isDefined,
      hasVideo = maybeVideo.isDefined,
      totalCount.get)

    val model = ImpressionBiddableInfoModel(
      impression,
      Some(rows),
      exactQueryRows,
      impressionAttributes)

    val logModel = LogTraceModel(
      methodName,
      request = Some(impression),
      entity = Some(model),
      databaseTransactionType = Some(DatabaseActionType.Read.toString))

    coreProperties.databaseLogger.trace(logModel)

    Some(model)
  }

  private def EmptyImpressionBiddableInfoFor(impression : ImpressionModel) = {
    Some(ImpressionBiddableInfoModel(
      impression,
      None,
      None,
      ImpressionBiddableAttributesModel(
        hasBanner = false,
        hasVideo = false,
        0)))
  }

  def getBiddableImpressionInfoModels(
    request : DemandSidePlatformBiddingRequestWrapperModel,
    limit : Int = defaultAdvertiseLimit) : Seq[ImpressionBiddableInfoModel] = {
    val repositories = coreProperties.repositories
    val advertiseRepository = repositories.advertiseRepository
    val advertises : TableQuery[Tables.Advertise] = repositories.advertises
    val impressions = request.bidRequestModel.imp.get

    val futureTasks : Seq[Future[ImpressionBiddableInfoModel]] =
      impressions.map(impression => {
        Future(
          getImpressionBiddableInfo(
            advertises,
            advertiseRepository,
            impression,
            limit).get
        )(createDefaultContext())
      })

    futureTasks.map(futureTask => FutureToRegular.toRegular(futureTask))
  }
}
