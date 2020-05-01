package shared.com.ortb.model.auctionbid.bidresponses

case class BidResponseModel(
  id : String,

  /**
   * Array of seatbid objects; 1+ required if a bid is to be made.
   */
  seatbid : Option[SeatBidModel],

  /**
   * Bidder generated response ID to assist with logging/tracking.
   */
  bidid : Option[String],

  /**
   * Bid currency using ISO-4217 alpha codes.
   * default “USD”
   */
  cur : Option[String] = Some("USD"),

  /**
   * Reason for not bidding.
   */
  nbr : Option[Int]
)
