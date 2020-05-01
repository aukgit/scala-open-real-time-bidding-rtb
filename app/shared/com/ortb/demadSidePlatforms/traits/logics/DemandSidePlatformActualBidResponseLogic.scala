package shared.com.ortb.demadSidePlatforms.traits.logics

import shared.com.ortb.model.DemandSidePlatformBidResponseModel
import shared.com.ortb.model.results.DemandSidePlatformBiddingRequestModel

trait DemandSidePlatformActualBidResponseLogic {
  def getBidActual(
    request : DemandSidePlatformBiddingRequestModel) : Option[DemandSidePlatformBidResponseModel]

  def getBidActualNoContent(
    request : DemandSidePlatformBiddingRequestModel) : Option[DemandSidePlatformBidResponseModel]
}
