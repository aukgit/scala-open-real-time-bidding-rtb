package shared.com.ortb.adapters.traits

import shared.com.ortb.model.auctionbid.biddingRequests.SiteModel

trait SiteModelAdapter {
  def getCategories(site : Option[SiteModel]) : Option[List[String]]

  def getCategoryId(site : Option[SiteModel]) : Option[String]
}

