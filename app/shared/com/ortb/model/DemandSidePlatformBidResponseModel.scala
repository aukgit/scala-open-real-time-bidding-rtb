package shared.com.ortb.model

import shared.com.ortb.importedModels.biddingRequests.{ BidRequestModel, BidResponseModel }
import shared.com.ortb.model.results.DspBidderRequestModel
import shared.io.helpers.IterableHelper

import scala.collection.mutable.ArrayBuffer

case class DemandSidePlatformBidResponseModel(
  request : DspBidderRequestModel,
  bidRequest      : BidRequestModel,
  bidResponse : BidResponseModel,
  deals : Option[List[ImpressionDealModel]] = None,
  isNoContent     : Boolean = false
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
