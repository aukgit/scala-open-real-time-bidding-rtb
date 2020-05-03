package shared.com.ortb.demadSidePlatforms

import shared.com.ortb.demadSidePlatforms.traits.properties.DemandSidePlatformBiddingProperties
import shared.com.ortb.model.results.DemandSidePlatformBiddingRequestModel
import shared.com.ortb.model.{ CallStackModel, auctionbid }
import shared.com.ortb.model.auctionbid.DemandSidePlatformBidResponseModel

trait DefaultActualNoContentResponse {
  this : DemandSidePlatformBiddingAgent =>

  def getBidActualNoContent(
    request : DemandSidePlatformBiddingRequestModel) : Option[DemandSidePlatformBidResponseModel] = {
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
