package shared.com.ortb.demadSidePlatforms

import shared.com.ortb.model.{ auctionbid, _ }
import shared.com.ortb.model.auctionbid.{ BidFailedInfoModel, BidFailedInfoWithRowsModel }
import shared.com.ortb.model.logging.LogTraceModel
import shared.com.ortb.model.results.DemandSidePlatformBiddingRequestModel
import shared.com.ortb.persistent
import shared.com.ortb.persistent.Repositories
import shared.com.ortb.persistent.schema._
import shared.io.helpers.NumberHelper
import slick.jdbc.SQLiteProfile.api._

import scala.util.Random
import shared.com.ortb.persistent.schema.Tables._

trait FailedBidsGetter {
  this : DemandSidePlatformBiddingAgent =>

  def getLastFailedDealsAsBidFailedInfoWithRowsModel(
    request : DemandSidePlatformBiddingRequestModel,
    limit : Int = defaultLimit) : BidFailedInfoWithRowsModel = {
    val methodName = "getLastFailedDealsAsBidFailedInfoWithRowsModel"

    val repositories = coreProperties.repositories
    val lostBids = repositories.lostBids

    val lostBidResults = getLostBids(
      limit,
      lostBids)

    val bidFailedInfoModel : BidFailedInfoModel = getBidFailedInfoModel(
      limit,
      lostBidResults)

    val bidFailedInfoWithRowsModel = BidFailedInfoWithRowsModel(
      attributes = bidFailedInfoModel,
      data = lostBidResults
    )

    val logTraceModel = LogTraceModel(
      methodName,
      Some(request),
      entity = Some(bidFailedInfoWithRowsModel))

    coreProperties.databaseLogger.trace(logTraceModel)

    bidFailedInfoWithRowsModel
  }

  private def getBidFailedInfoModel(
    limit : Int,
    lostBidResults : Seq[persistent.schema.Tables.LostbidRow]) = {
    val length = lostBidResults.length
    val repositories = coreProperties.repositories
    val averageOfLosingPrice = lostBidResults.map(w => NumberHelper.getAsDouble(w.losingprice)).sum / length
    val lastLosingPrice = NumberHelper.getAsDouble(lostBidResults.head.losingprice)

    val winningPriceModel = getWinningPricesModel(limit, repositories)

    val averageOfWiningPrices = winningPriceModel.averageOfWiningPrices
    val lastWiningPrice = winningPriceModel.lastWinningPrice

    val absoluteDifferenceOfAverageLosingAndWinningPrice =
      Math.abs(averageOfWiningPrices - averageOfLosingPrice)
    val absoluteDifferenceOfLosingAndWinningPrice =
      Math.abs(lastWiningPrice - lastLosingPrice)

    val randomInGuessRange : Seq[Double] = for (i <- 1 to 10) yield Random.between(
      randomNumberIncrementerGuessRange.start,
      randomNumberIncrementerGuessRange.end)

    val bidFailedInfoModel = auctionbid.BidFailedInfoModel(
      lastLostBid = lostBidResults.head,
      lastWinningBid = winningPriceModel.lastWinningBidRow,
      lastLosingPrice = lastLosingPrice,
      lastWiningPrice = lastWiningPrice,
      averageOfLosingPrices = averageOfLosingPrice,
      averageOfWiningPrices = averageOfWiningPrices,
      guessOfAdditionalPrices = randomInGuessRange,
      absoluteDifferenceOfAverageLosingAndWinningPrice = absoluteDifferenceOfAverageLosingAndWinningPrice,
      absoluteDifferenceOfLosingAndWinningPrice = absoluteDifferenceOfLosingAndWinningPrice
    )

    bidFailedInfoModel
  }

  def getLostBids(
    limit : Int,
    lostBids : TableQuery[Tables.Lostbid]) : Seq[Tables.LostbidRow] = {
    val repositories = coreProperties.repositories
    val demandSidePlatformId = coreProperties.demandSideId
    val lostBidsQuery = lostBids
      .filter(r => r.demandsideplatformid === demandSidePlatformId)
      .sortBy(_.losingprice.desc)
      .sortBy(_.createddate.desc)
      .take(limit)

    val lostBidsSqlProfileAction = lostBidsQuery
      .result

    val lostBidResults = repositories.lostBidRepository
      .run(lostBidsSqlProfileAction)

    lostBidResults
  }

  def getWinningPricesModel(
    limit : Int,
    repositories : Repositories) : WinningPricesModel = {
    val winningPriceInfoViewRepository = repositories.viewsRepositories
      .winningPriceInfoViewRepository
    val query  = winningPriceInfoViewRepository
      .getAllQuery
      .filter(w => w.iswon === 1)
      .sortBy(_.impressioncreateddate.desc)
      .take(limit)
      .result

    var results = winningPriceInfoViewRepository.getResults(query)

//    val bidResponses = repositories.bidResponses
//    val bidResponsesQuery = bidResponses.filter(r => r.iswontheauction === 0)
//      .sortBy(_.actualselectedprice.desc)
//      .sortBy(_.createddate.desc)
//      .take(limit)
//
//    val bidResponseResults = repositories
//      .bidResponseRepository
//      .run(bidResponsesQuery)
//
//    bidResponseResults
    throw new NotImplementedError()
  }
}

case class WinningPricesModel (
  averageOfWiningPrices: Double,
  lastWinningPrice: Double,
  lastWinningBidRow : WinningpriceinfoviewRow
)