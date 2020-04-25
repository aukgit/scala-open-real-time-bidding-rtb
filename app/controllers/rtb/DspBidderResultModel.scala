package controllers.rtb

import shared.com.ortb.importedModels.biddingRequests.{ BidRequest, Impression }
import shared.com.ortb.model.results.DspBidderRequestModel

case class ImpressionDealModel (
  impression: Impression,
  deal: Option[Double]
)

case class DspBidderResultModel (
  request : DspBidderRequestModel,
  bidRequest : BidRequest,
  deals : Option[List[ImpressionDealModel]],
  callStacks : Option[List[CallStackModel]]
)
