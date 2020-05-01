package shared.com.ortb.adapters.traits

import shared.com.ortb.enumeration.NoBidResponseType.NoBidResponseType
import shared.com.ortb.model.auctionbid.bidresponses.{ BidResponseModel, BidResponseModelWrapper }

trait BidResponseModelAdapter {
  def noBidResponse(noBidResponseType: NoBidResponseType) : BidResponseModelWrapper
}
