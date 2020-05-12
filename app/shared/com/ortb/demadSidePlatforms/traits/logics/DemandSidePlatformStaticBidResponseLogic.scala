package shared.com.ortb.demadSidePlatforms.traits.logics

import shared.com.ortb.model.auctionbid.DemandSidePlatformBidResponseModel
import shared.com.ortb.model.results.DemandSidePlatformBiddingRequestWrapperModel

trait DemandSidePlatformStaticBidResponseLogic {
  def getBidStatic(
    request : DemandSidePlatformBiddingRequestWrapperModel) : Option[DemandSidePlatformBidResponseModel]

  def getBidStaticNoContent(
    request : DemandSidePlatformBiddingRequestWrapperModel) : Option[DemandSidePlatformBidResponseModel]
}
