package controllers.rtb

import shared.com.ortb.importedModels.biddingRequests.ImpressionModel
import shared.com.ortb.model.results.DspBidderRequestModel

import scala.collection.mutable.ArrayBuffer

trait StaticBidResponses {
  def getBidStatic(
    request    : DspBidderRequestModel) : Option[DspBidderResultModel]

  def getBidStaticNoContent(
    request    : DspBidderRequestModel) : Option[DspBidderResultModel]
}

class StaticBidResponsesImplementation extends StaticBidResponses {
  override def getBidStatic(
    request: DspBidderRequestModel): Option[DspBidderResultModel] = {
    val impressions: Seq[ImpressionModel] = request.bidRequest.imp.get
    val length = impressions.length
    val deals = new ArrayBuffer[ImpressionDealModel](length)
    val callStacks = new ArrayBuffer[CallStackModel](length)

    for (impression <- impressions) {
      if (impression.bidFloor.isDefined) {
        val deal: Double = impression.bidFloor.get + defaultIncrementNumber
        val impressionDealModel = ImpressionDealModel(impression, Some(deal))
        deals.addOne(impressionDealModel)

        val callStackModel = CallStackModel(
          deal = deal,
          performingAction =
            s"[getBidStatic] -> adding deals($deal) for given bid request.",
          isServedAnyDeal = true
        )

        callStacks.addOne(callStackModel)
      } else {
        deals.addOne(ImpressionDealModel(impression, Some(defaultStaticDeal)))

        val callStackModel = CallStackModel(
          deal = defaultStaticDeal,
          performingAction =
            s"[getBidStatic] -> adding deals($defaultStaticDeal) for given bid request.",
          isServedAnyDeal = true
        )

        callStacks.addOne(callStackModel)
      }
    }

    val dspBidderResultModel =
      DspBidderResultModel(request, request.bidRequest, Some(deals.toList))
    dspBidderResultModel.addCallStacks(callStacks)

    Some(dspBidderResultModel)
  }

  override def getBidStaticNoContent(request : DspBidderRequestModel) : Option[DspBidderResultModel] = ???
}

trait DspBidder {
  def isStatic(request : DspBidderRequestModel) : Boolean = request.controller.config.server.isStaticSimulate

  def getBidPrices(request : DspBidderRequestModel): Option[DspBidderResultModel]

  def getBid(
    request : DspBidderRequestModel) : Option[DspBidderResultModel] = {

    if (isStatic(request)) {
      return getBidStatic(request)
    }

    getBidActual(request)
  }

  def getBidStatic(
    request    : DspBidderRequestModel) : Option[DspBidderResultModel]

  def getBidStaticNoContent(
    request    : DspBidderRequestModel) : Option[DspBidderResultModel]

  def getBidActual(
    request : DspBidderRequestModel) : Option[DspBidderResultModel]

  def getBidActualNoContent(
    request : DspBidderRequestModel) : Option[DspBidderResultModel]
}
