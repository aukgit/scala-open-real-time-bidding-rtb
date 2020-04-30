package shared.com.ortb.importedModels.biddingRequests

case class PrivateMarketPlaceDealModel(
  id: String,

  /**
   * Minimum bid for this impression expressed in CPM.
   */
  bidfloor: Option[Double] = Some(0),

  /**
   * Currency specified using ISO-4217 alpha codes.
   * This may be different from bid currency returned by bidder if this is allowed by the exchange.
   */
  bidfloorcur: Option[String] = Some("USD"),

  /**
   * Optional override of the overall auction type of the bid request,
   * where 1 = First Price, 2 = Second Price Plus, 3 = the value passed
   * in bidfloor is the agreed upon deal price.
   * Additional auction types can be defined by the exchange
   */
  at: Option[Int] = None,

  /**
   * Whitelist of buyer seats allowed to bid on this deal.
   * Seat IDs must be communicated between bidders and
   * the exchange a priori. Omission implies no seat restrictions
   */
  wseat: Option[List[String]] = None,

  /**
   * Array of advertiser domains (e.g., advertiser.com)
   * allowed to bid on this deal.
   * Omission implies no advertiser restrictions.
   */
  wadomain: Option[List[String]] = None,

)
