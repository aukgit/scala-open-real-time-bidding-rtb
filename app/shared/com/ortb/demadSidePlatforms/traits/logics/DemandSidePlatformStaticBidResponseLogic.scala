package shared.com.ortb.demadSidePlatforms.traits.logics

import shared.com.ortb.model.DemandSidePlatformBidResponseModel
import shared.com.ortb.model.results.DspBidderRequestModel

trait DemandSidePlatformStaticBidResponseLogic {
  def getBidStatic(
    request    : DspBidderRequestModel) : Option[DemandSidePlatformBidResponseModel]

  def getBidStaticNoContent(
    request    : DspBidderRequestModel) : Option[DemandSidePlatformBidResponseModel]
}
