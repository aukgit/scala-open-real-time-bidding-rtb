package controllers.rtb

import shared.com.ortb.importedModels.biddingRequests.BidRequest

case class DspBidderPrintResultModel(
  request: String,
  bidRequest: BidRequest,
  deals : Option[List[Double]],
  callStacks : Option[List[String]]
)
