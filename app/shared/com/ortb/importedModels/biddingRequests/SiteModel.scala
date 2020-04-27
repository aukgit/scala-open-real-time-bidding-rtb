package shared.com.ortb.importedModels.biddingRequests

case class SiteModel(
  id : String,
  domain : Option[String],
  page: Option[String],
  cat: Option[List[String]],
  publisher: Option[PublisherModel]
)


