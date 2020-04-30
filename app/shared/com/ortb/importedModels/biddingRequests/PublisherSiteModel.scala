package shared.com.ortb.importedModels.biddingRequests

case class PublisherSiteModel(
  id : Int,
  cat: Option[List[String]],
  name: Option[String],
  domain : Option[String]
)
