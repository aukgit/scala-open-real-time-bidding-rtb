package shared.com.ortb.demadSidePlatforms

import shared.com.ortb.model.results.DspBidderRequestModel
import shared.com.ortb.model.{ BidFailedInfoModel, ImpressionBiddableInfoModel, ImpressionDealModel }
import shared.io.helpers.EmptyValidateHelper

import scala.concurrent.Future

trait ImpressionDealsGetter {
  this : DemandSidePlatformBiddingAgent =>

  def getImpressionInfoModelFromImpressionBiddableInfoModel(
    bidFailedReasonsModel: BidFailedInfoModel,
    impressionBiddableInfoModel : ImpressionBiddableInfoModel) : ImpressionDealModel = {
    if (impressionBiddableInfoModel == null ||
      !impressionBiddableInfoModel.attributes.isBiddable) {
      return null
    }

    val banner = impressionBiddableInfoModel.impression.banner.get
    val lastFailed = bidFailedReasonsModel.lostBids.av

    if (EmptyValidateHelper.hasAnyItem(impressionBiddableInfoModel.exactHeightWidthAdvertises)) {
      // more price
      return ImpressionDealModel(impressionBiddableInfoModel.impression, )

    }

    // val items = exactAdvertises.toList

    ???
  }

  def getImpressionInfoModelsFromImpressionBiddableInfoModels(
    request : DspBidderRequestModel,
    biddableImpressionInfoModels : Seq[ImpressionBiddableInfoModel]) :
  Option[List[ImpressionDealModel]] = {
    if (EmptyValidateHelper.isItemsEmpty(Some(biddableImpressionInfoModels))) {
      return None
    }

    val list = biddableImpressionInfoModels.map(b => {
      val attr = b.attributes
      val isEmpty = !attr.isBiddable && attr.advertisesFoundCount == 0
      Future {
        addNewAdvertiseIfNoAdvertiseInTheGivenCriteria(
          request,
          isEmpty,
          b)
      }

      getImpressionInfoModelFromImpressionBiddableInfoModel(b)
    }).filter(w => w != null).toList

    Some(list)
    throw new NotImplementedError()
  }
}
