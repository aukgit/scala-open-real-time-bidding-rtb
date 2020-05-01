package shared.com.ortb.model.auctionbid.biddingRequests

case class PublisherSiteModel(
  id : Int,
  cat: Option[List[String]],
  name: Option[String],
  domain : Option[String]
)
