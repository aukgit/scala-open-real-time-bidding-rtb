package shared.com.ortb.model.auctionbid

case class ImpressionBiddableAttributesModel(
  hasBanner : Boolean,
  hasVideo : Boolean,
  advertisesFoundCount : Int
) {
  lazy val hasBannerOrVideo : Boolean = hasVideo || hasBanner
  lazy val isBiddable : Boolean = advertisesFoundCount > 0
}
