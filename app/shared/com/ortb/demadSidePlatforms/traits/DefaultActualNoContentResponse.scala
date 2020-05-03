package shared.com.ortb.demadSidePlatforms.traits

import shared.com.ortb.demadSidePlatforms.DemandSidePlatformBiddingAgent
import shared.com.ortb.model.auctionbid
import shared.com.ortb.model.auctionbid.DemandSidePlatformBidResponseModel
import shared.com.ortb.model.logging.CallStackModel
import shared.com.ortb.model.results.DemandSidePlatformBiddingRequestWrapperModel

trait DefaultActualNoContentResponse {
  this : DemandSidePlatformBiddingAgent =>

  def getBidActualNoContent(
    request : DemandSidePlatformBiddingRequestWrapperModel) : Option[DemandSidePlatformBidResponseModel] = {
    val dspBidderResultModel =
      auctionbid.DemandSidePlatformBidResponseModel(request, request.bidRequestModel, isNoContent = true,bidResponseWrapper = null)

    val callStackModel = CallStackModel(
      deal = coreProperties.noDealPrice,
      performingAction = s"[getBidActualNoContent] -> No deals."
    )

    dspBidderResultModel.addCallStack(callStackModel)

    Some(dspBidderResultModel)
  }
}
