package controllers.rtb

case class CallStackModel(
  deal: Double = 0,
  performingAction: String = "",
  message: String = "",
  isServedAnyDeal: Boolean = false,
  possibleOutcome: String = ""
)
