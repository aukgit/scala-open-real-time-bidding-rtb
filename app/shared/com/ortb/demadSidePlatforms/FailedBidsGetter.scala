package shared.com.ortb.demadSidePlatforms
import shared.com.ortb.model.{ BidFailedInfoModel, BidFailedInfoWithRowsModel, LogTraceModel }
import shared.com.ortb.model.results.DspBidderRequestModel
import slick.jdbc.SQLiteProfile.api._

trait FailedBidsGetter {
  this : DemandSidePlatformBiddingAgent =>

  def getLastFailedDeals(
    request : DspBidderRequestModel,
    limit : Int = defaultLimit) : BidFailedInfoWithRowsModel = {
    val demandSidePlatformId = coreProperties.demandSideId
    val repositories = coreProperties.repositories
    val lostBids = repositories.lostBids

    val lostBidsQuery = lostBids
      .filter(r => r.demandsideplatformid === demandSidePlatformId)
      .sortBy(_.losingprice.desc)
      .sortBy(_.createddate.desc)
      .take(limit)

    val lostBidsSqlProfileAction = lostBidsQuery
      .result

    val 

    val results = repositories.lostBidRepository
      .run(lostBidsSqlProfileAction)

    val logTraceModel = LogTraceModel(
      "getLastFailedDeals",
      Some(request),
      entities = Some(results))

    coreProperties.databaseLogger.trace(logTraceModel)

//    BidFailedInfoModel(
//
//    )
    ???
  }
}
