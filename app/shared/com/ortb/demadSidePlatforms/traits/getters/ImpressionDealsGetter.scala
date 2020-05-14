package shared.com.ortb.demadSidePlatforms.traits.getters

import shared.com.ortb.demadSidePlatforms.DemandSidePlatformBiddingAgent
import shared.com.ortb.model.auctionbid.{ BidFailedInfoModel, ImpressionBiddableInfoModel, ImpressionDealModel }
import shared.com.ortb.model.config.RangeModel
import shared.com.ortb.model.results.DemandSidePlatformBiddingRequestWrapperModel
import shared.io.helpers.{ EmptyValidateHelper, NumberHelper }

import scala.concurrent.Future
import scala.util.Random

trait ImpressionDealsGetter {
  this : DemandSidePlatformBiddingAgent =>
  lazy val randomNumberIncrementerGuessRange : RangeModel = coreProperties.randomNumberIncrementerGuessRange

  def getImpressionInfoModelFromImpressionBiddableInfoModel(
    bidFailedReasons : BidFailedInfoModel,
    impressionBiddableInfo : ImpressionBiddableInfoModel) : ImpressionDealModel = {
    val isNonBiddable = bidFailedReasons == null ||
      impressionBiddableInfo == null ||
      !impressionBiddableInfo
        .attributes
        .isBiddable

    if (isNonBiddable) {
      return null
    }

    val randomIncrements = randomNumberIncrementerGuessRange.guessRandomInBetween
    val hasExactHeightsWidths = EmptyValidateHelper.hasAnyItem(
      impressionBiddableInfo.exactHeightWidthAdvertises)
    val bidFloor = NumberHelper.getAsDouble(impressionBiddableInfo.impression.bidfloor)

    if (hasExactHeightsWidths) {
      // more price
      val randomInBetweenAvgWinAndLoss = bidFailedReasons
        .randomNumberBetweenAverageLosingAndWinningPriceOrStaticIncrementIfNoDifference

      val deal =
        bidFloor +
          randomInBetweenAvgWinAndLoss +
          bidFailedReasons.averageOfWiningPrices +
          randomIncrements +
          coreProperties.defaultIncrementNumber

      return ImpressionDealModel(impressionBiddableInfo.impression, deal)
    }

    var threshold : Double = 0

    if (bidFloor <= 0) {
      val initial = coreProperties.defaultStaticDeal - coreProperties.defaultStaticDeal * 0.9
      threshold = Random.between(initial, coreProperties.defaultStaticDeal)
    } else {
      threshold = bidFloor
    }

    val deal = threshold +
      randomIncrements +
      bidFailedReasons.absoluteDifferenceOfAverageLosingAndWinningPrice +
      coreProperties.defaultIncrementNumber

    ImpressionDealModel(impressionBiddableInfo.impression, deal)
  }

  def getImpressionInfoModelsFromImpressionBiddableInfoModels(
    request : DemandSidePlatformBiddingRequestWrapperModel,
    biddableImpressionInfoModels : Seq[ImpressionBiddableInfoModel]) :
  Option[List[ImpressionDealModel]] = {
    if (EmptyValidateHelper.isItemsEmpty(Some(biddableImpressionInfoModels))) {
      return None
    }

    val bidFailedInfoWithRowsModel = getLastFailedDealsAsBidFailedInfoWithRows(
      request)

    val list = biddableImpressionInfoModels.map(b => {
      val attr = b.attributes
      val isEmpty = !attr.isBiddable && attr.advertisesFoundCount == 0

      addNewAdvertiseIfNoAdvertiseInTheGivenCriteriaAsync(
        request,
        isEmpty,
        b)

      getImpressionInfoModelFromImpressionBiddableInfoModel(
        bidFailedInfoWithRowsModel.attributes,
        b)
    }).filter(w => w != null).toList

    Some(list)
  }
}
