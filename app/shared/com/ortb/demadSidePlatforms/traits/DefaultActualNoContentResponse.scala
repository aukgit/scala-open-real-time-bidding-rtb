package shared.com.ortb.demadSidePlatforms.traits

import com.github.dwickern.macros.NameOf._
import shared.com.ortb.demadSidePlatforms.DemandSidePlatformBiddingAgent
import shared.com.ortb.model.auctionbid.DemandSidePlatformBidResponseModel
import shared.com.ortb.model.logging.CallStackModel
import shared.com.ortb.model.results.DemandSidePlatformBiddingRequestWrapperModel

trait DefaultActualNoContentResponse {
  this : DemandSidePlatformBiddingAgent =>

  def getBidActualNoContent(
    request : DemandSidePlatformBiddingRequestWrapperModel) : Option[DemandSidePlatformBidResponseModel] = {
    val methodName = nameOf(getBidActualNoContent _)
    val dspBidderResultModel =
      DemandSidePlatformBidResponseModel(
        request,
        request.bidRequest,
        isNoContent = true,
        bidResponseWrapper = null)

    // save no bid response
    addBidResponseAsync(dspBidderResultModel)

    Some(dspBidderResultModel)
  }
}
