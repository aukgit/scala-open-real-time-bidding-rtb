package shared.com.ortb.adapters.traits

import shared.com.ortb.importedModels.biddingRequests.BannerModel
import shared.com.ortb.importedModels.campaign.SimpleBanner
import shared.io.helpers.NumberHelper

class BannerModelAdapterConcreteImplementation extends BannerModelAdapter {
  def getSimpleBanner(bannerModel : BannerModel) : SimpleBanner = {
    if (bannerModel == null) {
      return null
    }

    SimpleBanner(
      bannerModel.id,
      wmin = NumberHelper.getAsInt(bannerModel.wmin),
      wmax = NumberHelper.getAsInt(bannerModel.wmax),
      w = NumberHelper.getAsInt(bannerModel.w),
      hmin = NumberHelper.getAsInt(bannerModel.hmin),
      hmax = NumberHelper.getAsInt(bannerModel.hmax),
      h = NumberHelper.getAsInt(bannerModel.h))
  }
}
