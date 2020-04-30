package shared.com.ortb.importedModels.campaign

import shared.com.ortb.importedModels.biddingRequests.BannerImpressionModel

case class CampaignModel(
  id : Int, userId : Int, country : String, targeting : TargetingModel, banners : List[BannerImpressionModel], bid : Double)
