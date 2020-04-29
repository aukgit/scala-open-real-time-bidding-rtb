package shared.com.ortb.adapters.traits

import shared.com.ortb.importedModels.biddingRequests.SiteModel

trait SiteModelAdapter {
  def getCategories(site : Option[SiteModel]) : Option[List[String]]

  def getCategoryId(site : Option[SiteModel]) : Option[String]
}

