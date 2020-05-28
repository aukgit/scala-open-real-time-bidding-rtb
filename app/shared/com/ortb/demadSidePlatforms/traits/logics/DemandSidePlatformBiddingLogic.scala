package shared.com.ortb.demadSidePlatforms.traits.logics

import shared.com.ortb.model.auctionbid.DemandSidePlatformBidResponseModel
import shared.com.ortb.model.auctionbid.biddingRequests.BidRequestModel
import shared.com.ortb.model.results.DemandSidePlatformBiddingRequestWrapperModel
import shared.com.ortb.persistent.schema.Tables

trait DemandSidePlatformBiddingLogic
  extends DemandSidePlatformStaticBidResponseLogic
    with DemandSidePlatformActualBidResponseLogic {
  def isStatic : Boolean

  def getBidRequestToBidRequestRow(bidRequest : BidRequestModel) : Tables.BidrequestRow = {
    if (isStatic) {
      return getStaticBidRequestToBidRequestRow(bidRequest)
    }

    getActualBidRequestToBidRequestRow(bidRequest)
  }

  def getBidPrices(request : DemandSidePlatformBiddingRequestWrapperModel) : Option[DemandSidePlatformBidResponseModel]

  def getBid(
    request : DemandSidePlatformBiddingRequestWrapperModel) : Option[DemandSidePlatformBidResponseModel] = {

    if (isStatic) {
      return getStaticBid(request)
    }

    getBidActual(request)
  }
}
