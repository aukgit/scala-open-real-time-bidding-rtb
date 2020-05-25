package shared.com.ortb.model.auctionbid

import shared.com.ortb.model.auctionbid.biddingRequests.ImpressionModel
import shared.com.ortb.persistent.schema.Tables._

case class ImpressionDealModel(
  impression : ImpressionModel,
  advertise : AdvertiseRow,
  deal : Double
) {
  lazy val hasAnyDeal : Boolean = deal > 0
}
