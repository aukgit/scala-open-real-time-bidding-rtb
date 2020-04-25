package controllers.rtb

import shared.com.ortb.importedModels.biddingRequests.BidRequest
import shared.com.ortb.model.results.DspBidderRequestModel

trait DspBidder {
  def getBid(
    request: DspBidderRequestModel) : BidRequest = {
    val isStatic = request.controller.config.server.isStaticSimulate

    if(isStatic){
      return getBidStatic(request)
    }

    getBidActual(request)
  }

  def getBidStatic(
    request: DspBidderRequestModel) : BidRequest

  def getBidStaticNoContent(
    request: DspBidderRequestModel) : BidRequest

  def getBidActual(
    request: DspBidderRequestModel) : BidRequest

  def getBidActualNoContent(
    request: DspBidderRequestModel) : BidRequest
}
