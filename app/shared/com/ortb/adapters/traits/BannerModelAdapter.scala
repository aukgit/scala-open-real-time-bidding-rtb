package shared.com.ortb.adapters.traits

import shared.com.ortb.importedModels.biddingRequests.BannerImpressionModel
import shared.com.ortb.importedModels.campaign.SimpleBannerModel

trait BannerModelAdapter {
  def getSimpleBanner(d : BannerImpressionModel) : SimpleBannerModel
}
