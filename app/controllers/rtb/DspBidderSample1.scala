package controllers.rtb

import shared.com.ortb.enumeration.DemandSidePlatformBiddingAlgorithmType.DemandSidePlatformBiddingAlgorithmType
import shared.com.ortb.importedModels.biddingRequests.BidRequest
import shared.com.ortb.model.results.DspBidderRequestModel

class DspBidderSample1 (algorithmType: DemandSidePlatformBiddingAlgorithmType)
  extends DspBidder {
  override def getBidStatic(
    request : DspBidderRequestModel) : BidRequest = ???

  override def getBidStaticNoContent(
    request : DspBidderRequestModel) : BidRequest = ???

  override def getBidActual(
    request : DspBidderRequestModel) : BidRequest = ???

  override def getBidActualNoContent(
    request : DspBidderRequestModel) : BidRequest = ???
}
