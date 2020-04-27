package shared.com.ortb.model
import shared.com.ortb.persistent.schema.Tables._

case class BidFailedReasonsModel(
  lostBids: Seq[LostbidRow]
)
