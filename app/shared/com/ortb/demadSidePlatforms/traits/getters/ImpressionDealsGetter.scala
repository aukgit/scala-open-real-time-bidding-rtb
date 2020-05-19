package shared.com.ortb.demadSidePlatforms.traits.getters

import com.github.dwickern.macros.NameOf._
import shared.com.ortb.demadSidePlatforms.DemandSidePlatformBiddingAgent
import shared.com.ortb.model.auctionbid.{ BidFailedInfoModel, ImpressionBiddableInfoModel, ImpressionDealModel }
import shared.com.ortb.model.config.RangeModel
import shared.com.ortb.model.results.DemandSidePlatformBiddingRequestWrapperModel
import shared.com.ortb.persistent.schema.Tables
import shared.io.helpers.{ EmptyValidateHelper, NumberHelper }
import shared.io.loggers.AppLogger

import scala.util.Random

trait ImpressionDealsGetter {
  this : DemandSidePlatformBiddingAgent =>
  lazy val randomNumberIncrementerGuessRange : RangeModel = coreProperties.randomNumberIncrementerGuessRange

  def getImpressionInfoModelsFromImpressionBiddableInfoModels(
    request : DemandSidePlatformBiddingRequestWrapperModel,
    biddableImpressionInfos : Seq[ImpressionBiddableInfoModel]) :
  Option[List[ImpressionDealModel]] = {
    if (EmptyValidateHelper.isItemsEmpty(Some(biddableImpressionInfos))) {
      return None
    }

    val bidFailedInfoWithRowsModel = getLastFailedDealsAsBidFailedInfoWithRows(
      request)

    val list = biddableImpressionInfos.map(b => {
      val attr = b.attributes

      addNewAdvertiseIfNoAdvertiseInTheGivenCriteriaAsync(
        request,
        !attr.isBiddable,
        b)

      if (EmptyValidateHelper.hasAnyItem(b.advertises)) {
        b.advertises.get.map(advertise => {
          getImpressionInfoModelFromImpressionBiddableInfoModel(
            bidFailedInfoWithRowsModel.attributes,
            b,
            advertise)
        })
      } else {
        null
      }

    }).filter(w => w != null).toList

    Some(list)
  }

  def getImpressionInfoModelFromImpressionBiddableInfoModel(
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
