package shared.com.ortb.demadSidePlatforms.traits.logics

import shared.com.ortb.model.DemandSidePlatformBidResponseModel
import shared.com.ortb.model.results.DspBidderRequestModel

trait DemandSidePlatformBiddingLogic
  extends DemandSidePlatformStaticBidResponseLogic
    with DemandSidePlatformActualBidResponseLogic{
  def isStatic : Boolean

  def getBidPrices(request : DspBidderRequestModel) : Option[DemandSidePlatformBidResponseModel]

  def getBid(
    request : DspBidderRequestModel) : Option[DemandSidePlatformBidResponseModel] = {

    if (isStatic) {
      return getBidStatic(request)
    }

    getBidActual(request)
  }
}
