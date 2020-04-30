package shared.com.ortb.importedModels.biddingRequests

case class ImpressionModel(
  id : String,

  /**
   * Minimum bid for this impression expressed in CPM.
   */
  bidfloor : Option[Double],

  /**
   * Currency specified using ISO-4217 alpha codes.
   * This may be different from bid currency returned by bidder if this is allowed by the exchange.
   */
  bidfloorcur: Option[String] = Some("USD"),

  banner: Option[BannerImpressionModel] = None,
  video: Option[VideoImpressionModel] = None,
  pmp: Option[PrivateMarketPlaceModel] = None,
)

