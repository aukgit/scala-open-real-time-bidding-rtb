package shared.com.ortb.adapters.traits

import shared.com.ortb.model.auctionbid.biddingRequests.SiteModel
import shared.io.helpers.EmptyValidateHelper

class SiteModelConcreteAdapterImplementation extends SiteModelAdapter{
  def getCategories(site : Option[SiteModel]) : Option[List[String]] = {
    if (site.isEmpty) {
      return None
    }

    if (site.get.cat.isDefined) {
      return site.get.cat
    }

    None
  }

  def getCategoryId(site : Option[SiteModel]) : Option[String] = {
    val categories = getCategories(site)
    if (EmptyValidateHelper.hasAnyItem(categories)) {
      return Some(categories.get.head)
    }

    None
  }
}
