package shared.com.ortb.adapters.traits

import shared.com.ortb.enumeration.NoBidResponseType.NoBidResponseType
import shared.com.ortb.importedModels.BidResponseModelWrapper
import shared.com.ortb.importedModels.biddingRequests.{ BannerImpressionModel, BidResponseModel }
import shared.com.ortb.importedModels.campaign.SimpleBannerModel
import shared.io.helpers.NumberHelper

class BidResponseModelAdapterConcreteImplementation extends BidResponseModelAdapter {
  override def noBidResponse(DemandSidePlatformRequest, noBidResponseType : NoBidResponseType) : BidResponseModelWrapper = {
    BidResponseModel()
  }
}


class BannerModelAdapterConcreteImplementation extends BannerModelAdapter {
  def getSimpleBanner(bannerModel : BannerImpressionModel) : SimpleBannerModel = {
    if (bannerModel == null) {
      return null
    }

    SimpleBannerModel(
      bannerModel.id,
      wmin = NumberHelper.getAsInt(bannerModel.wmin),
      wmax = NumberHelper.getAsInt(bannerModel.wmax),
      w = NumberHelper.getAsInt(bannerModel.w),
      hmin = NumberHelper.getAsInt(bannerModel.hmin),
      hmax = NumberHelper.getAsInt(bannerModel.hmax),
      h = NumberHelper.getAsInt(bannerModel.h))
  }
}
