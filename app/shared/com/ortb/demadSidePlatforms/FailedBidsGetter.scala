package shared.com.ortb.demadSidePlatforms

import shared.com.ortb.model._
import shared.com.ortb.model.results.DspBidderRequestModel
import shared.com.ortb.persistent
import shared.com.ortb.persistent.Repositories
import shared.com.ortb.persistent.schema._
import shared.io.helpers.NumberHelper
import slick.jdbc.SQLiteProfile.api._

import scala.util.Random

trait FailedBidsGetter {
  this : DemandSidePlatformBiddingAgent =>

  def getLastFailedDealsAsBidFailedInfoWithRowsModel(
    request : DspBidderRequestModel,
    limit : Int = defaultLimit) : BidFailedInfoWithRowsModel = {
    val methodName = "getLastFailedDealsAsBidFailedInfoWithRowsModel"

    val repositories = coreProperties.repositories
    val lostBids = repositories.lostBids

    val lostBidResults = getLostBids(
      limit,
      repositories,
      lostBids)

    val bidFailedInfoModel : BidFailedInfoModel = getBidFailedInfoModel(
      limit,
      repositories,
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
    repositories : Repositories,
    lostBidResults : Seq[persistent.schema.Tables.LostbidRow]) = {
    val bidResponses = repositories.bidResponses
    val length = lostBidResults.length

    val averageOfLosingPrice = lostBidResults.map(w => NumberHelper.getAsDouble(w.losingprice)).sum / length
    val lastLosingPrice = NumberHelper.getAsDouble(lostBidResults.head.losingprice)

    val bidResponseResults = getBidResponses(limit, repositories, bidResponses)

    val bidResponseResultsLength = bidResponseResults.length
    val averageOfWiningPrices = bidResponseResults.map(w => w.actualselectedprice).sum / bidResponseResultsLength
    val lastWiningPrice = NumberHelper.getAsDouble(lostBidResults.head.losingprice)

    val absoluteDifferenceOfAverageLosingAndWinningPrice =
      Math.abs(averageOfWiningPrices - averageOfLosingPrice)
    val absoluteDifferenceOfLosingAndWinningPrice =
      Math.abs(lastWiningPrice - lastLosingPrice)

    val randomInGuessRange : Seq[Double] = for (i <- 1 to 10) yield Random.between(
      randomNumberIncrementerGuessRange.start,
      randomNumberIncrementerGuessRange.end)

    val bidFailedInfoModel = BidFailedInfoModel(
      lastLostBid = lostBidResults.head,
      lastWinningBid = bidResponseResults.head,
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
    repositories : Repositories,
    lostBids : TableQuery[Tables.Lostbid]) : Seq[Tables.LostbidRow] = {
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

  def getBidResponses(
    limit : Int,
    repositories : Repositories,
    bidResponses : TableQuery[Tables.Bidresponse]) : Seq[Tables.BidresponseRow] = {
    val bidResponsesQuery = bidResponses.filter(r => r.iswontheauction === 0)
      .sortBy(_.actualselectedprice.desc)
      .sortBy(_.createddate.desc)
      .take(limit)

    val bidResponseResults = repositories
      .bidResponseRepository
      .run(bidResponsesQuery)

    bidResponseResults
  }
}
