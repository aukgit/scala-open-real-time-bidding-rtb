package shared.com.ortb.importedModels.campaign

import shared.com.ortb.importedModels.biddingRequests.BannerModel

case class CampaignModel(
  id : Int, userId : Int, country : String, targeting : TargetingModel, banners : List[BannerModel], bid : Double)
