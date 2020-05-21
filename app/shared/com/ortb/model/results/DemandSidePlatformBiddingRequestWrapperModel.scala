package shared.com.ortb.model.results

import shared.com.ortb.model.auctionbid.biddingRequests.BidRequestModel
import shared.com.ortb.persistent.schema.Tables._

case class DemandSidePlatformBiddingRequestWrapperModel(
  bidRequest : BidRequestModel,
  bidRequestRow : BidrequestRow,
  dspId : Int)
