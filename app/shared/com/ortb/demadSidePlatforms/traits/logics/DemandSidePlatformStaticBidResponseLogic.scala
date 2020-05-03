package shared.com.ortb.demadSidePlatforms.traits.logics

import shared.com.ortb.model.auctionbid.DemandSidePlatformBidResponseModel
import shared.com.ortb.model.results.DemandSidePlatformBiddingRequestModel

trait DemandSidePlatformStaticBidResponseLogic {
  def getBidStatic(
    request    : DemandSidePlatformBiddingRequestModel) : Option[DemandSidePlatformBidResponseModel]

  def getBidStaticNoContent(
    request    : DemandSidePlatformBiddingRequestModel) : Option[DemandSidePlatformBidResponseModel]
}
