package shared.com.ortb.model

import shared.com.ortb.importedModels.biddingRequests.BidRequest

case class DspBidderPrintResultModel(
  request: String,
  bidRequest: BidRequest,
  deals : Option[List[String]],
  callStacks : Option[List[String]]
)
