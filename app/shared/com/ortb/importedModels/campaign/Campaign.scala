package shared.com.ortb.importedModels.campaign

import shared.com.ortb.importedModels.biddingRequests.BannerModel

case class Campaign(
  id : Int, userId : Int, country : String, targeting : Targeting, banners : List[BannerModel], bid : Double)
