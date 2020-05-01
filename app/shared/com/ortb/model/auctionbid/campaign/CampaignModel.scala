package shared.com.ortb.model.auctionbid.campaign

import shared.com.ortb.model.auctionbid.biddingRequests.banners.BannerImpressionModel

case class CampaignModel(
  id : Int, userId : Int, country : String, targeting : TargetingModel, banners : List[BannerImpressionModel], bid : Double)
