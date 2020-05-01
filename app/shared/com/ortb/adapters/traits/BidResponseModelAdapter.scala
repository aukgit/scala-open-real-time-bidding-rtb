package shared.com.ortb.adapters.traits

import shared.com.ortb.enumeration.NoBidResponseType.NoBidResponseType
import shared.com.ortb.model.auctionbid.bidresponses.BidResponseModelWrapper
import shared.com.ortb.model.results.DemandSidePlatformBiddingRequestModel

trait BidResponseModelAdapter {
  /**
   * A well-formed no bid response with a reason code: {"id": "1234567890", "seatbid": [], "nbr": 2}
   * @param request
   * @param noBidResponseType
   *
   * @return
   */
  def noBidResponse(
    request : DemandSidePlatformBiddingRequestModel,
    noBidResponseType : NoBidResponseType) : BidResponseModelWrapper
}
