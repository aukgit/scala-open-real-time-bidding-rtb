package shared.com.ortb.model.auctionbid
import shared.com.ortb.persistent.schema.Tables._

case class WinningPricesModel (
  averageOfWiningPrices: Double,
  lastWinningPrice: Double,
  lastWinningBidRow : WinningpriceinfoviewRow
) {
  lazy val hasAnyPrice: Boolean = lastWinningBidRow != null
}
