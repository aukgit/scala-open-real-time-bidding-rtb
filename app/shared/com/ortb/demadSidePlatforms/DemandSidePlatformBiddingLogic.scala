package shared.com.ortb.demadSidePlatforms

import shared.com.ortb.model.DspBidderResultModel
import shared.com.ortb.model.results.DspBidderRequestModel

trait DemandSidePlatformBiddingLogic
  extends DemandSidePlatformStaticBidResponseLogic
    with DemandSidePlatformActualBidResponseLogic{
  def isStatic : Boolean

  def getBidPrices(request : DspBidderRequestModel) : Option[DspBidderResultModel]

  def getBid(
    request : DspBidderRequestModel) : Option[DspBidderResultModel] = {

    if (isStatic) {
      return getBidStatic(request)
    }

    getBidActual(request)
  }
}
