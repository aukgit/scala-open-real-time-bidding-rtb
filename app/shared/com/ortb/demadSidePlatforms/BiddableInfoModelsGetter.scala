package shared.com.ortb.demadSidePlatforms
import shared.com.ortb.model.auctionbid.biddingRequests.ImpressionModel
import shared.com.ortb.model.results.DemandSidePlatformBiddingRequestModel
import shared.com.ortb.model.{ LogTraceModel, auctionbid }
import shared.com.ortb.model.auctionbid.{ ImpressionBiddableAttributesModel, ImpressionBiddableInfoModel }
import shared.com.ortb.persistent.repositories.AdvertiseRepository
import shared.com.ortb.persistent.schema
import shared.com.ortb.persistent.schema.Tables
import shared.com.repository.traits.FutureToRegular
import shared.io.helpers.EmptyValidateHelper
import slick.lifted.TableQuery
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.Future

trait BiddableInfoModelsGetter {
  this : DemandSidePlatformBiddingAgent =>

  def getImpressionBiddableInfoModel(
    advertisesTable : TableQuery[Tables.Advertise],
    advertiseRepository : AdvertiseRepository,
    impression : ImpressionModel,
    limit : Int = defaultAdvertiseLimit) : Option[ImpressionBiddableInfoModel] = {
    val methodName = "getImpressionBiddableInfoModel"

    if (EmptyValidateHelper.isEmpty(impression.banner)) {
      // no banner
      val model = auctionbid.ImpressionBiddableInfoModel(
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

    val model = auctionbid.ImpressionBiddableInfoModel(
      impression,
      Some(rows.toArray),
      exactQueryRows,
      impressionAttributes)

    val logModel = LogTraceModel(
      methodName,
      request = Some(impression),
      entity = Some(model))

    coreProperties.databaseLogger.trace(logModel)

    Some(model)
  }

  def getBiddableImpressionInfoModels(
    request : DemandSidePlatformBiddingRequestModel,
    limit : Int = defaultAdvertiseLimit) : Seq[ImpressionBiddableInfoModel] = {
    val repositories = coreProperties.repositories
    val advertiseRepository = repositories.advertiseRepository
    val advertises : TableQuery[Tables.Advertise] = repositories.advertises
    val impressions = request.bidRequestModel.imp.get

    val futureTasks : Seq[Future[ImpressionBiddableInfoModel]] =
      impressions.map(impression => {
        Future(
          getImpressionBiddableInfoModel(
            advertises,
            advertiseRepository,
            impression,
            limit).get
        )
      })

    futureTasks.map(futureTask => FutureToRegular.toRegular(futureTask))
  }
}
