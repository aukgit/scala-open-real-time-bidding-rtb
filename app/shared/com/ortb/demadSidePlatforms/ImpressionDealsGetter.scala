package shared.com.ortb.demadSidePlatforms

import shared.com.ortb.model._
import shared.com.ortb.model.auctionbid.{ BidFailedInfoModel, ImpressionBiddableInfoModel, ImpressionDealModel }
import shared.com.ortb.model.config.RangeModel
import shared.com.ortb.model.results.DemandSidePlatformBiddingRequestModel
import shared.io.helpers.EmptyValidateHelper

import scala.concurrent.Future
import scala.util.Random

trait ImpressionDealsGetter {
  this : DemandSidePlatformBiddingAgent =>
  lazy val randomNumberIncrementerGuessRange : RangeModel = coreProperties.randomNumberIncrementerGuessRange

  def getImpressionInfoModelFromImpressionBiddableInfoModel(
    bidFailedReasonsModel : BidFailedInfoModel,
    impressionBiddableInfoModel : ImpressionBiddableInfoModel) : ImpressionDealModel = {
    val isNonBiddable = bidFailedReasonsModel == null ||
      impressionBiddableInfoModel == null ||
      !impressionBiddableInfoModel.attributes.isBiddable

    if (isNonBiddable) {
      return null
    }

    val banner = impressionBiddableInfoModel.impression.banner.get
    val hasExactHeightsWidths = EmptyValidateHelper.hasAnyItem(
      impressionBiddableInfoModel.exactHeightWidthAdvertises)


    if (hasExactHeightsWidths) {
      // more price
      val length = bidFailedReasonsModel.guessOfAdditionalPrices.length
      val randomIndex = Random.between(0, length)
      val randomIncrements = bidFailedReasonsModel.guessOfAdditionalPrices(randomIndex)
      val deal =
        bidFailedReasonsModel.absoluteDifferenceOfAverageLosingAndWinningPrice +
          bidFailedReasonsModel.averageOfWiningPrices +
          randomIncrements +
          coreProperties.defaultIncrementNumber

      return ImpressionDealModel(impressionBiddableInfoModel.impression, deal)
    }

    val deal =
      coreProperties.defaultStaticDeal +
      bidFailedReasonsModel.absoluteDifferenceOfAverageLosingAndWinningPrice +
        coreProperties.defaultIncrementNumber

    ImpressionDealModel(impressionBiddableInfoModel.impression, deal)
  }

  def getImpressionInfoModelsFromImpressionBiddableInfoModels(
    request : DemandSidePlatformBiddingRequestModel,
    biddableImpressionInfoModels : Seq[ImpressionBiddableInfoModel]) :
  Option[List[ImpressionDealModel]] = {
    if (EmptyValidateHelper.isItemsEmpty(Some(biddableImpressionInfoModels))) {
      return None
    }

    val bidFailedInfoWithRowsModel = getLastFailedDealsAsBidFailedInfoWithRowsModel(
      request)

    val list = biddableImpressionInfoModels.map(b => {
      val attr = b.attributes
      val isEmpty = !attr.isBiddable && attr.advertisesFoundCount == 0
      Future {
        addNewAdvertiseIfNoAdvertiseInTheGivenCriteria(
          request,
          isEmpty,
          b)
      }

      getImpressionInfoModelFromImpressionBiddableInfoModel(
        bidFailedInfoWithRowsModel.attributes,
        b)
    }).filter(w => w != null).toList

    Some(list)
  }
}
