package shared.com.ortb.model.auctionbid.bidresponses

case class BidResponseModelWrapper(
  bidResponse : Option[BidResponseModel] = None,
  httpStatusCode : Option[Int] = Some(200))
