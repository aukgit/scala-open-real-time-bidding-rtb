package controllers.rtb

import shared.com.ortb.model.results.DspBidderRequestModel

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
