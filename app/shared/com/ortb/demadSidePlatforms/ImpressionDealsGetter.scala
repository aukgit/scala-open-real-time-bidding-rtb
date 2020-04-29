package shared.com.ortb.demadSidePlatforms
import shared.com.ortb.model.{ ImpressionBiddableInfoModel, ImpressionDealModel }
import shared.com.ortb.model.results.DspBidderRequestModel
import shared.io.helpers.EmptyValidateHelper

import scala.concurrent.Future

trait ImpressionDealsGetter {
  this : DemandSidePlatformBiddingAgent =>

  def getImpressionDealsFromBiddableImpressionInfoModels(
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
    }).toList

    // create deals
    biddableImpressionInfoModels.map(w => {
      if (!w.attributes.isBiddable) {
        return None
      }
      val banner = w.impression.banner.get
      var advs = w.advertises.get.to(LazyList)

      if (banner.h.isDefined) {
        advs = advs.filter(r => banner.h.get == r.height)
      }

      if (banner.w.isDefined) {
        advs = advs.filter(r => banner.w.get == r.width)
      }

      val items = advs.toList

    })

    Some(list)
    throw new NotImplementedError()
  }
}
