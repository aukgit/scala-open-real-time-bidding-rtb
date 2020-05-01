package shared.com.ortb.adapters.traits

import shared.com.ortb.model.auctionbid.biddingRequests.banners.{ BannerImpressionModel, SimpleBannerModel }

trait BannerModelAdapter {
  def getSimpleBanner(d : BannerImpressionModel) : SimpleBannerModel
}
