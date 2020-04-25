package controllers.rtb

import shared.com.ortb.enumeration.DemandSidePlatformBiddingAlgorithmType.DemandSidePlatformBiddingAlgorithmType
import shared.com.ortb.importedModels.biddingRequests.{ BidRequest, Impression }
import shared.com.ortb.model.results.DspBidderRequestModel

import scala.collection.mutable.ArrayBuffer

class DspBidderSample1 (algorithmType: DemandSidePlatformBiddingAlgorithmType)
  extends DspBidder {
  override def getBidStatic(
    request : DspBidderRequestModel) : Option[DspBidderResultModel] = {
    val impressions : Seq[Impression] = request.bidRequest.imp.get
    val length = impressions.length
    val deals = new ArrayBuffer[ImpressionDealModel](length)

    for(impression <- impressions){
      if(impression.bidFloor.isDefined){
        val deal : Double = impression.bidFloor.get + 0.01
        deals.addOne(ImpressionDealModel(impression, Some(deal)))
      }
      else {
        deals.addOne(ImpressionDealModel(impression, Some(0.03)))
      }
    }

    val callStackModels = new CallStackModel()

    DspBidderResultModel(request, request.bidRequest, Some(deals.toList), )

  }

  override def getBidStaticNoContent(
    request : DspBidderRequestModel) : Option[DspBidderResultModel] = ???

  override def getBidActual(
    request : DspBidderRequestModel) : Option[DspBidderResultModel] = ???

  override def getBidActualNoContent(
    request : DspBidderRequestModel) : Option[DspBidderResultModel] = ???
}
