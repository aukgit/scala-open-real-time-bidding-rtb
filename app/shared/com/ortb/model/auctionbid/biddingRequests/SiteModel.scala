package shared.com.ortb.model.auctionbid.biddingRequests

case class SiteModel(
  id : String,
  domain : Option[String],
  page: Option[String],

  /**
   * Category : IAB15, IAB15-1, IAB17 (sports)
   */
  cat: Option[List[String]],
  content: Option[ContentSiteModel] = None,
  publisher: Option[PublisherSiteModel]
)




