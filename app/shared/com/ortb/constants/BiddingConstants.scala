package shared.com.ortb.constants

import shared.io.extensions.TypeConvertExtensions._
import shared.com.ortb.model.auctionbid.bidresponses.BidModel

sealed class BiddingConstants {
  lazy val aDomains = List(
    "Advertiser  blocked domains list",
    "Sample-Site-blocked.com",
    "Sample-Site2-blocked.com",
    "Sample-Site3-blocked.com")

  lazy val staticBidModel : BidModel = BidModel(
    "static:bidId",
    "-1",
    0,
    "static:Advertise Id".toSome,
    None,
    "adm win-notice markup, optional and should be ignored.".toSome,
    aDomains.toSome,
    "iurl : uncached image link".toSome,
    "static: CampaignId".toSome,
    List("static: categoryId1", "static: categoryId2").toSome,
    "static: dealId".toSome,
    h = None,
    w = None
  )
}
