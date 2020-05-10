package shared.com.ortb.model.logging

case class CallStackModel(
  deal : Double = 0,
  performingAction : String = "",
  message : String = "",
  isServedAnyDeal : Boolean = false,
  possibleOutcome : String = ""
)
