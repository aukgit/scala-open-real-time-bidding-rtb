package shared.com.ortb.importedModels.biddingRequests

case class SeatBidModel (
  bid: List[BidModel]
)

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
   * Win notice URL called by the exchange if the bid wins; optional means of serving ad markup.
   */
  nurl: Option[String],
)
