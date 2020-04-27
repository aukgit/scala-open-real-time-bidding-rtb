package shared.com.ortb.demadSidePlatforms.traits.logics

import shared.com.ortb.model.DspBidderResultModel
import shared.com.ortb.model.results.DspBidderRequestModel

trait DemandSidePlatformStaticBidResponseLogic {
  def getBidStatic(
    request    : DspBidderRequestModel) : Option[DspBidderResultModel]

  def getBidStaticNoContent(
    request    : DspBidderRequestModel) : Option[DspBidderResultModel]
}
