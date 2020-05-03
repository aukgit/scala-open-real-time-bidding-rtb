package shared.com.ortb.model.auctionbid

import shared.com.ortb.model.auctionbid.biddingRequests.BidRequestModel
import shared.com.ortb.model.auctionbid.bidresponses.BidResponseModelWrapper
import shared.com.ortb.model.logging.CallStackModel
import shared.com.ortb.model.results.DemandSidePlatformBiddingRequestModel
import shared.io.helpers.IterableHelper

import scala.collection.mutable.ArrayBuffer

case class DemandSidePlatformBidResponseModel(
  request : DemandSidePlatformBiddingRequestModel,
  bidRequest : BidRequestModel,
  bidResponseWrapper : BidResponseModelWrapper,
  deals : Option[List[ImpressionDealModel]] = None,
  isNoContent : Boolean = false
) {
  private val callStacks : ArrayBuffer[CallStackModel] = new ArrayBuffer[CallStackModel](3)

  def addCallStack(callStackModel : CallStackModel) : Unit = {
    callStacks.addOne(callStackModel)
  }

  def addCallStacks(callStackModels : Iterable[CallStackModel]) : Unit = {
    callStacks.addAll(callStackModels)
  }

  def getCallStacks : List[CallStackModel] = {
    callStacks.toList
  }

  def toPrintModel : DspBidderPrintResultModel = {
    val callStacksAsStrings = IterableHelper.toListStrings(Some(getCallStacks))
    val dealsAsStrings = IterableHelper.toListStrings(deals)

    DspBidderPrintResultModel(
      request.toString,
      bidRequest,
      dealsAsStrings,
      callStacksAsStrings
    )
  }
}
