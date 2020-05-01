package shared.com.ortb.adapters.traits

import shared.com.ortb.enumeration.NoBidResponseType.NoBidResponseType
import shared.com.ortb.importedModels.BidResponseModelWrapper
import shared.com.ortb.importedModels.biddingRequests.BidResponseModel

trait BidResponseModelAdapter {
  def noBidResponse(noBidResponseType: NoBidResponseType) : BidResponseModelWrapper
}
