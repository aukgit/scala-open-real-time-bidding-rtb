package shared.com.ortb.adapters.traits

import shared.com.ortb.importedModels.biddingRequests.BannerModel
import shared.com.ortb.importedModels.campaign.SimpleBanner

trait BannerModelAdapter {
  def getSimpleBanner(d : BannerModel) : SimpleBanner
}
