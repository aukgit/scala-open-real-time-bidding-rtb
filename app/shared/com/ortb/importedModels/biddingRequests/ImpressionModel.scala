package shared.com.ortb.importedModels.biddingRequests

/**
 *
 * @param id : id of the impression
 * @param bidfloor    : Minimum bid for this impression expressed in CPM.
 * @param bidfloorcur : Currency specified using ISO-4217 alpha codes.
 *                    * This may be different from bid currency returned
 *                    * by bidder if this is allowed by the exchange.
 * @param banner : Banner request information.
 * @param video : Video request information.
 * @param pmp : Private market place deals
 */
case class ImpressionModel(
  id : String,

  /**
   * Minimum bid for this impression expressed in CPM.
   */
  bidfloor : Option[Double],

  /**
   * Currency specified using ISO-4217 alpha codes.
   * This may be different from bid currency returned
   * by bidder if this is allowed by the exchange.
   */
  bidfloorcur: Option[String] = Some("USD"),

  /**
   * Banner request information.
   */
  banner: Option[BannerImpressionModel] = None,

  /**
   * Video request information.
   */
  video: Option[VideoImpressionModel] = None,

  /**
   * Private market place deals
   */
  pmp: Option[PrivateMarketPlaceModel] = None,
)

