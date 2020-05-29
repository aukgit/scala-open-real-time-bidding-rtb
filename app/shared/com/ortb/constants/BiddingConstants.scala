package shared.com.ortb.constants

import shared.com.ortb.model.auctionbid.bidresponses.BidModel
import shared.io.extensions.TypeConvertExtensions._

sealed class BiddingConstants {
  lazy val aDomains = List(
    "Advertiser blocked domains list",
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

  /**
   * HTTP 204 “No Content” from the bidder (most economical in terms of bandwidth).
   *  An empty JSON object: {}
   *  A well-formed no bid response: {"id": "1234567890", "seatbid": []}
   *  A well-formed no bid response with a reason code: {"id": "1234567890", "seatbid": [], "nbr": 2}
   */
  lazy val emptyStaticBidResponse = "{\"id\": \"\", \"seatbid\": []}"

  /**
   * HTTP 204 “No Content” from the bidder (most economical in terms of bandwidth).
   *  An empty JSON object: {}
   *  A well-formed no bid response: {"id": "1234567890", "seatbid": []}
   *  A well-formed no bid response with a reason code: {"id": "1234567890", "seatbid": [], "nbr": 2}
   */
  lazy val emptyStaticBidResponseWithNoBidResponseCodeUnknown = "{\"id\": \"\", \"seatbid\": [], \"nbr\": 0}"

  lazy val winNoticePlaceHolderName = "$impressionId"
}
