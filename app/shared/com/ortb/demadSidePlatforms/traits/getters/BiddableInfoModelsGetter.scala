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

    if (EmptyValidateHelper.isEmpty(impression.banner)) {
      // no banner
      val model = ImpressionBiddableInfoModel(
        impression,
        None,
        None,
        ImpressionBiddableAttributesModel(
          isBiddable = false,
          hasBanner = false,
          0))

      return Some(model)
    }

    val banner = impression.banner.get
    val advertisesQueryIn = advertisesTable.filter(advertise =>
      advertise.isvideo === 0)

    val advertisesQuery = appendQueryForBanner(advertisesQueryIn, banner)
    val countQuery = advertisesQuery.length.result
    val query = advertisesQuery.take(limit).result
    val exactQueryRows = getExactHeightWidthQueryRows(
      advertiseRepository,
      advertisesQuery,
      banner)

    val totalCount = advertiseRepository.count(countQuery)
    val rows = advertiseRepository.run(query)
    val isBiddable = rows.nonEmpty

    val impressionAttributes = ImpressionBiddableAttributesModel(
      isBiddable,
      hasBanner = true,
      totalCount.get)

    val model = ImpressionBiddableInfoModel(
      impression,
      Some(rows.toArray),
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
