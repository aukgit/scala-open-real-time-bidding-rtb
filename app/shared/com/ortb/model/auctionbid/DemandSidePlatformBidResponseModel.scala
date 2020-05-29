package shared.com.ortb.model.auctionbid

import shared.com.ortb.model.auctionbid.biddingRequests.BidRequestModel
import shared.com.ortb.model.auctionbid.bidresponses.BidResponseModelWrapper
import shared.com.ortb.model.logging.CallStackModel
import shared.com.ortb.model.results.DemandSidePlatformBiddingRequestWrapperModel
import shared.io.helpers.IterableHelper

import scala.collection.mutable.ArrayBuffer

case class DemandSidePlatformBidResponseModel(
  request : DemandSidePlatformBiddingRequestWrapperModel,
  bidRequest : BidRequestModel,
  bidResponseWrapper : BidResponseModelWrapper,
  deals : Option[List[ImpressionDealModel]] = None,
  isNoContent : Boolean = false
) {
  lazy val toPrintModel : DspBidderPrintResultModel = {
    val dealsAsStrings = IterableHelper.toListStrings(deals)

    DspBidderPrintResultModel(
      request.toString,
      bidRequest,
      dealsAsStrings
    )
  }
}
