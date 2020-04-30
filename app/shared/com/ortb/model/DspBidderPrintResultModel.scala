package shared.com.ortb.model

import shared.com.ortb.importedModels.biddingRequests.BidRequestModel

case class DspBidderPrintResultModel(
  request: String,
  bidRequest: BidRequestModel,
  deals : Option[List[String]],
  callStacks : Option[List[String]]
)
