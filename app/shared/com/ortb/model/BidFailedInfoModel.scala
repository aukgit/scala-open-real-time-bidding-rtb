package shared.com.ortb.model
import shared.com.ortb.persistent.schema.Tables._

case class BidFailedInfoModel(
  lastLostBid: LostbidRow,
  lastWinningBid : BidresponseRow,

  lastLosingPrice : Double,
  lastWiningPrice : Double,

  /**
   * By Config Limit
   */
  averageOfLosingPrices: Double,

  /**
   * By Config Limit
   */
  averageOfWiningPrices: Double,

  guessOfAdditionalPrices : Seq[Double],
  absoluteDifferenceOfAverageLosingAndWinningPrice: Double,
  absoluteDifferenceOfLosingAndWinningPrice: Double,
)

