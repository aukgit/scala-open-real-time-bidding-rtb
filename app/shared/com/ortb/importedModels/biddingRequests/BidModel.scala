package shared.com.ortb.importedModels.biddingRequests

/**
 * BidResponse -> seatbid[] -> seatbid -> bid[] -> single BidModel is this one.
 *
 * @param id : (Must) Identifier for bid
 * @param impid : (Must) ID of the Imp (Impression) object in the related bid request.
 * @param price : (Must) Bid price expressed as CPM although the
 *              * actual transaction is for a unit impression only.
 *              * Note that while the type indicates float,
 *              * integer math is highly recommended
 *              * when handling currencies (e.g., BigDecimal in Java).
 * @param adid : ID of a preloaded ad to be served if the bid wins.
 * @param nurl : Win notice URL called by the exchange if the bid wins;
 *             * optional means of serving ad markup.
 *             * The win notice URL and its format are defined by the bidder.
 *             * In order for the exchange to convey certain information to
 *             * the winning bidder (e.g., the clearing price), a number of substitution
 *             * macros can be inserted into the win notice URL definition.
 *             * Prior to calling a win notice URL, the exchange will search
 *             * the specified URL for any of the defined macros and replace
 *             * them with the appropriate data. Note that the substitution is
 *             * simple in the sense that wherever a legal macro is found,
 *             * it will be replaced without regard for syntax correctness.
 * @param adm     : Optional means of conveying ad markup in
 *                * case the bid wins; supersedes the
 *                * win notice if markup is included in both.
 * @param adomain : Advertiser domain for block list checking (e.g., “ford.com”).
 *                * This can be an array of for the case of rotating creatives.
 *                * Exchanges can mandate that only one domain is allowed.
 * @param iurl    : URL without cache-busting to an image that is
 *                * representative of the content of the
 *                * campaign for ad quality/safety checking.
 * @param cid     : Campaign ID to assist with ad quality checking; t
 *                * he collection of creatives for which iurl
 *                * should be representative.
 * @param cat    : IAB content categories
 * @param dealid : Reference to the deal.id from the bid request
 *               * if this bid pertains to a private marketplace direct deal.
 * @param h : Height of the creative in pixels.
 * @param w : Width of the creative in pixels.
 */
case class BidModel(
  id: String,

  /**
   * ID of the Imp (Impression) object in the related bid request.
   */
  impid : String,

  /**
   * Bid price expressed as CPM although the
   * actual transaction is for a unit impression only.
   * Note that while the type indicates float,
   * integer math is highly recommended
   * when handling currencies (e.g., BigDecimal in Java).
   */
  price: Double,

  /**
   * ID of a preloaded ad to be served if the bid wins.
   */
  adid: Option[String],


  /**
   * Win notice URL called by the exchange if the bid wins;
   * optional means of serving ad markup.
   * The win notice URL and its format are defined by the bidder.
   * In order for the exchange to convey certain information to
   * the winning bidder (e.g., the clearing price), a number of substitution
   * macros can be inserted into the win notice URL definition.
   * Prior to calling a win notice URL, the exchange will search
   * the specified URL for any of the defined macros and replace
   * them with the appropriate data. Note that the substitution is
   * simple in the sense that wherever a legal macro is found,
   * it will be replaced without regard for syntax correctness.
   */
  nurl: Option[String],

  /**
   * Optional means of conveying ad markup in
   * case the bid wins; supersedes the
   * win notice if markup is included in both.
   */
  adm: Option[String],

  /**
   * Advertiser domain for block list checking (e.g., “ford.com”).
   * This can be an array of for the case of rotating creatives.
   * Exchanges can mandate that only one domain is allowed.
   */
  adomain: Option[List[String]],

  /**
   * URL without cache-busting to an image that is
   * representative of the content of the
   * campaign for ad quality/safety checking.
   */
  iurl: Option[String],


  /**
   * Campaign ID to assist with ad quality checking; t
   * he collection of creatives for which iurl
   * should be representative.
   */
  cid: Option[String],


  /**
   * IAB content categories
   */
  cat: Option[List[String]],

  /**
   * Reference to the deal.id from the bid request
   * if this bid pertains to a private marketplace direct deal.
   */
  dealid :Option[String],

  /**
   * Height of the creative in pixels.
   */
  h :Option[Int],

  /**
   * Width of the creative in pixels.
   */
  w :Option[Int],

)
