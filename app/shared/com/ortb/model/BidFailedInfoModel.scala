package shared.com.ortb.model
import shared.com.ortb.persistent.schema.Tables._

case class BidFailedInfoModel(
  lostBids: Seq[LostbidRow],
  lastLostBid: LostbidRow,

  /**
   * By Config Limit
   */
  averageOfLosingPrices: Double,

  /**
   * By Config Limit
   */
  averageOfWiningPrices: Double,
)


