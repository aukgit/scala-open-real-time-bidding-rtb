package shared.com.ortb.model.auctionbid
import shared.com.ortb.persistent.schema.Tables._

case class BidFailedInfoModel(
  lastLostBid: LostbidRow,
  lastWinningBid : WinningpriceinfoviewRow,

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
