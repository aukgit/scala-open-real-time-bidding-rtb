package controllers.rtb

import shared.com.ortb.enumeration.DemandSidePlatformBiddingAlgorithmType.DemandSidePlatformBiddingAlgorithmType
import shared.com.ortb.importedModels.biddingRequests.Impression
import shared.com.ortb.model.results.DspBidderRequestModel

import scala.collection.mutable.ArrayBuffer

class DspBidderSample1(algorithmType : DemandSidePlatformBiddingAlgorithmType)
  extends DspBidder {
  val defaultIncrementNumber = 0.01
  val defaultStaticDeal = 0.03

  override def getBidStatic(
    request : DspBidderRequestModel) : Option[DspBidderResultModel] = {
    val impressions : Seq[Impression] = request.bidRequest.imp.get
    val length = impressions.length
    val deals = new ArrayBuffer[ImpressionDealModel](length)
    val callStacks = new ArrayBuffer[CallStackModel](length)

    for (impression <- impressions) {
      if (impression.bidFloor.isDefined) {
        val deal : Double = impression.bidFloor.get + defaultIncrementNumber
        val impressionDealModel = ImpressionDealModel(impression, Some(deal))
        deals.addOne(impressionDealModel)

        val callStackModel = CallStackModel(
          deal = deal,
          performingAction = s"[getBidStatic] -> adding deals($deal) for given bid request.",
          isServedAnyDeal = true
        )

        callStacks.addOne(callStackModel)
      }
      else {
        deals.addOne(ImpressionDealModel(impression, Some(defaultStaticDeal)))

        val callStackModel = CallStackModel(
          deal = defaultStaticDeal,
          performingAction = s"[getBidStatic] -> adding deals($defaultStaticDeal) for given bid request.",
          isServedAnyDeal = true
        )

        callStacks.addOne(callStackModel)
      }
    }

    val dspBidderResultModel = DspBidderResultModel(request, request.bidRequest, Some(deals.toList))
    dspBidderResultModel.addCallStacks(callStacks)

    Some(dspBidderResultModel)
  }

  override def getBidStaticNoContent(
    request : DspBidderRequestModel) : Option[DspBidderResultModel] = {
    val dspBidderResultModel = DspBidderResultModel(
      request,
      request.bidRequest,
      isNoContent = true)

    val callStackModel = CallStackModel(
      deal = defaultStaticDeal,
      performingAction = s"[getBidStaticNoContent] -> No deals.",
      isServedAnyDeal = true
    )

    dspBidderResultModel.addCallStack(callStackModel)

    Some(dspBidderResultModel)
  }

  def getLastFailedDeals(request : DspBidderRequestModel) = {
    val controller = request.controller
    val w = controller.repositories.
  }

  override def getBidActual(
    request : DspBidderRequestModel) : Option[DspBidderResultModel] = {

  }

  override def getBidActualNoContent(
    request : DspBidderRequestModel) : Option[DspBidderResultModel] = ???
}
