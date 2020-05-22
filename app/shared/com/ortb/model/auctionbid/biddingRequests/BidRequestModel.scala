package shared.com.ortb.model.auctionbid.biddingRequests

/**
 *
 * @param id      : Bidding Id
 * @param imp     : Gives details of requesting advertise type Banner, Video or Native one.
 * @param site    : Site is used for targeting sites
 * @param user    : Site is used for targeting sites
 * @param device  : Device based on advertise details.
 * @param test    : Indicates non billable
 * @param at      : Default(2), auction type for first price 2nd price.
 * @param tmax    : Maximum time in milliseconds to submit a bid to avoid timeout. This value is commonly communicated offline.
 * @param wseat   : Whitelist of buyer seats allowed to bid on this impression.
 *                Seat IDs must be communicated between bidders and the exchange
 *                a priori. Omission implies no seat restrictions.
 * @param allimps : Flag to indicate if Exchange can verify that the impressions
 *                offered represent all of the impressions available in context
 *                (e.g., all on the web page, all video spots such as pre/mid/post roll)
 *                to support road-blocking. 0 = no or unknown, 1 = yes, the
 *                impressions offered represent all that are available.
 * @param cur     : Array of allowed currencies for bids on this bid request using ISO-4217 alpha codes.
 *                Recommended only if the exchange accepts multiple currencies.
 * @param bcat    : Blocked advertiser categories using the IAB content categories. Refer to List 5.1.
 * @param badv    : Block list of advertisers by their domains (e.g., “ford.com”).
 */
case class BidRequestModel(
  id : String,

  /**
   * Required Field
   * Gives details of requesting advertise type Banner, Video or Native one.
   */
  imp : List[ImpressionModel],

  /**
   * Recommended
   * Site is used for targeting sites
   */
  @deprecated("Depreciated in spec 2.5 (https://bit.ly/3bTJv1l)")
  site : Option[SiteModel] = None,

  /**
   * Indicator of test mode in which auctions are not billable,
   * where 0 = live mode, 1 = test mode.
   * Non Billable indication.
   */
  test : Option[Int] = Some(2),

  /**
   * Recommended
   * User is used for targeting user detail, like by age, gender and so on.
   */
  user : Option[UserModel] = None,

  /**
   * Recommended
   * Device based on advertise details.
   */
  device : Option[DeviceModel] = None,

  /**
   * Auction type, where 1 = First Price, 2 = Second Price Plus.
   * Exchange-specific auction types
   * can be defined using values greater than 500.
   */
  at : Option[Int] = Some(2),

  /**
   * Maximum time in milliseconds to submit a bid to avoid timeout. This value is commonly communicated offline.
   */
  tmax : Option[Int] = None,

  /**
   * Whitelist of buyer seats allowed to bid on this impression.
   * Seat IDs must be communicated between bidders and
   * the exchange a priori. Omission implies no seat restrictions.
   */
  wseat : Option[List[String]] = None,

  /**
   * Flag to indicate if Exchange can verify that the impressions
   * offered represent all of the impressions available in context
   * (e.g., all on the web page,
   * all video spots such as pre/mid/post roll)
   * to support road-blocking. 0 = no or
   * unknown, 1 = yes, the impressions offered
   * represent all that are available.
   */
  allimps : Option[Int] = Some(0),

  /**
   * Array of allowed currencies for
   * bids on this bid request using ISO-4217 alpha codes.
   * Recommended only if the exchange accepts multiple currencies.
   */
  cur : Option[List[String]] = None,

  /**
   * Blocked advertiser categories using the IAB content categories.
   * Refer to List 5.1.
   */
  bcat : Option[List[String]] = None,

  /**
   * Block list of advertisers by their domains (e.g., “ford.com”).
   */
  badv : Option[List[String]] = None)




