package shared.com.ortb.adapters.traits

import shared.com.ortb.importedModels.biddingRequests.BannerModel
import shared.com.ortb.importedModels.campaign.SimpleBannerModel

trait BannerModelAdapter {
  def getSimpleBanner(d : BannerModel) : SimpleBannerModel
}
