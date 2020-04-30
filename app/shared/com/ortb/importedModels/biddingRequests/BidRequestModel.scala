package shared.com.ortb.importedModels.biddingRequests

case class BidRequestModel(
  id : String,
  imp : Option[List[ImpressionModel]],
  site : Option[SiteModel],
  user : Option[UserModel],
  device : Option[DeviceModel])