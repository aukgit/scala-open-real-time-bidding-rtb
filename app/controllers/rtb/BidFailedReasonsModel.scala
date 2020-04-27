package controllers.rtb

case class BidFailedReasonsModel(
  lostBids: Seq[LostbidRow]
)
