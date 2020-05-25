package shared.com.ortb.demadSidePlatforms.traits

import shared.com.ortb.demadSidePlatforms.DemandSidePlatformBiddingAgent
import shared.com.ortb.model.auctionbid.DemandSidePlatformBidResponseModel
import shared.com.ortb.model.logging.CallStackModel
import shared.com.ortb.model.results.DemandSidePlatformBiddingRequestWrapperModel

trait DefaultActualNoContentResponse {
  this : DemandSidePlatformBiddingAgent =>

  def getBidActualNoContent(
    request : DemandSidePlatformBiddingRequestWrapperModel) : Option[DemandSidePlatformBidResponseModel] = {
    val dspBidderResultModel =
      DemandSidePlatformBidResponseModel(
        request,
        request.bidRequest,
        isNoContent = true, bidResponseWrapper = null)

    val callStackModel = CallStackModel(
      deal = coreProperties.noDealPrice,
      performingAction = s"[getBidActualNoContent] -> No deals."
    )

    // save no bid response
    addBidResponseAsync(dspBidderResultModel)

    dspBidderResultModel.addCallStack(callStackModel)

    Some(dspBidderResultModel)
  }
}
