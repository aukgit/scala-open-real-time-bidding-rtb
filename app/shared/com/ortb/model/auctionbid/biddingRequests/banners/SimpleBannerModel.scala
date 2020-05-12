package shared.com.ortb.model.auctionbid.biddingRequests.banners

case class SimpleBannerModel(
  id : Option[String],
  wmin : Int,
  wmax : Int,
  w : Int,
  hmin : Int,
  hmax : Int,
  h : Int)
