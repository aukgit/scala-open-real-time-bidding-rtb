package shared.com.ortb.demadSidePlatforms.traits.logics

import shared.com.ortb.model.auctionbid.DemandSidePlatformBidResponseModel
import shared.com.ortb.model.auctionbid.biddingRequests.BidRequestModel
import shared.com.ortb.model.results.DemandSidePlatformBiddingRequestWrapperModel
import shared.com.ortb.persistent.schema.Tables

trait DemandSidePlatformActualBidResponseLogic {
  def getBidActual(
    request : DemandSidePlatformBiddingRequestWrapperModel) : Option[DemandSidePlatformBidResponseModel]

  def getBidActualNoContent(
    request : DemandSidePlatformBiddingRequestWrapperModel) : Option[DemandSidePlatformBidResponseModel]

  def getActualBidRequestToBidRequestRow(bidRequest : BidRequestModel) : Tables.BidrequestRow
}
