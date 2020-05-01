package shared.com.ortb.model.results

import shared.com.ortb.demadSidePlatforms.DemandSidePlatformSimulatorServiceApiController
import shared.com.ortb.model.auctionbid.biddingRequests.BidRequestModel

case class DemandSidePlatformBiddingRequestModel(
  bidRequest  : BidRequestModel,
  dspId       : Int)
