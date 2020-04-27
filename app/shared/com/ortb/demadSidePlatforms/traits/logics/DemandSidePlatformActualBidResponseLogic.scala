package shared.com.ortb.demadSidePlatforms.traits.logics

import shared.com.ortb.model.DemandSidePlatformBidResponseModel
import shared.com.ortb.model.results.DspBidderRequestModel

trait DemandSidePlatformActualBidResponseLogic {
  def getBidActual(
    request : DspBidderRequestModel) : Option[DemandSidePlatformBidResponseModel]

  def getBidActualNoContent(
    request : DspBidderRequestModel) : Option[DemandSidePlatformBidResponseModel]
}
