package shared.com.ortb.importedModels.biddingRequests

case class ImpressionModel(
  id : String,
  banner: Option[BannerModel],
  bidFloor : Option[Double])
