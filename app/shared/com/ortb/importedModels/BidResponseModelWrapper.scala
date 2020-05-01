package shared.com.ortb.importedModels

import shared.com.ortb.importedModels.biddingRequests.BidResponseModel

case class BidResponseModelWrapper(
  bidResponse : Option[BidResponseModel] = None,
  httpStatusCode: Option[Int] = Some(200))
