package shared.com.ortb.demadSidePlatforms.traits.getters

import com.github.dwickern.macros.NameOf._
import shared.com.ortb.demadSidePlatforms.DemandSidePlatformBiddingAgent
import shared.com.ortb.model.auctionbid.{ BidFailedInfoModel, ImpressionBiddableInfoModel, ImpressionDealModel }
import shared.com.ortb.model.config.RangeModel
import shared.com.ortb.model.results.DemandSidePlatformBiddingRequestWrapperModel
import shared.com.ortb.persistent.schema.Tables
import shared.com.repository.traits.FutureToRegular
import shared.io.helpers.{ EmptyValidateHelper, NumberHelper }
import shared.io.loggers.AppLogger

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.Future
import scala.util.Random

trait ImpressionDealsGetter {
  this : DemandSidePlatformBiddingAgent =>
  lazy val randomNumberIncrementerGuessRange : RangeModel = coreProperties.randomNumberIncrementerGuessRange

  def getImpressionInfosFromImpressionBiddableInfos(
    request : DemandSidePlatformBiddingRequestWrapperModel,
    impressionBiddableInfos : Seq[ImpressionBiddableInfoModel]) :
  Option[List[ImpressionDealModel]] = {
    if (EmptyValidateHelper.isItemsEmpty(Some(impressionBiddableInfos))) {
      return None
    }

    val bidFailedInfoWithRowsModel = getLastFailedDealsAsBidFailedInfoWithRows(
      request)

    val capacity = impressionBiddableInfos.length * 10
    val eventualImpressionDeals = new ArrayBuffer[Future[ImpressionDealModel]](capacity)

    impressionBiddableInfos.foreach(impressionBiddableInfo => {
      val attributes = impressionBiddableInfo.attributes

      addNewAdvertiseIfNoAdvertiseInTheGivenCriteriaAsync(
        request,
        !attributes.isBiddable,
        impressionBiddableInfo)

      if (EmptyValidateHelper.hasAnyItem(impressionBiddableInfo.advertisesWithLimit)) {
        impressionBiddableInfo.advertisesWithLimit.get.foreach(advertise => {
          val eventualImpressionDeal = Future {
            getImpressionInfoFromImpressionBiddableInfo(
              bidFailedInfoWithRowsModel.attributes,
              impressionBiddableInfo,
              advertise)
          }(createDefaultContext())

          eventualImpressionDeals.addOne(eventualImpressionDeal)
        })
      }
    })

    val impressionDeals = eventualImpressionDeals
      .map(w => FutureToRegular.toRegular(w))
      .toList

    Some(impressionDeals)
  }

  def getImpressionInfoFromImpressionBiddableInfo(
    bidFailedReasons : BidFailedInfoModel,
    impressionBiddableInfo : ImpressionBiddableInfoModel,
    selectedAdvertise : Tables.AdvertiseRow) : ImpressionDealModel = {
    val isEmptyBidFailedReasons = bidFailedReasons == null
    val isNoBiddableAttribute = impressionBiddableInfo == null ||
      !impressionBiddableInfo
        .attributes
        .isBiddable

    val isNonBiddable = isEmptyBidFailedReasons ||
      isNoBiddableAttribute ||
      EmptyValidateHelper.isEmptyDirect(selectedAdvertise)

    if (isNonBiddable) {
      AppLogger.debug(
        nameOf(isNonBiddable),
        isNonBiddable.toString)

      return null
    }

    val randomIncrements = randomNumberIncrementerGuessRange.guessRandomInBetween
    val bidFloor = NumberHelper.getAsDouble(impressionBiddableInfo.impression.bidfloor)
    val randomInBetweenAvgWinAndLoss = bidFailedReasons
      .randomNumberBetweenAverageLosingAndWinningPriceOrStaticIncrementIfNoDifference
    var threshold : Double = 0
    if (bidFloor <= 0) {
      val initial = coreProperties.defaultStaticDeal - coreProperties.defaultStaticDeal * 0.9
      threshold = Random.between(initial, coreProperties.defaultStaticDeal)
    } else {
      threshold = bidFloor
    }

    val deal =
      threshold +
        randomInBetweenAvgWinAndLoss +
        bidFailedReasons.averageOfWiningPrices +
        randomIncrements

    ImpressionDealModel(
      impressionBiddableInfo.impression,
      selectedAdvertise,
      deal)
  }
}
