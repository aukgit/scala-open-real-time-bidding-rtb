package shared.io.loggers.traits

import shared.com.ortb.constants.AppConstants

trait LoggerProperties {
  val header = "[Open RTB]"
  val newLine : String = AppConstants.NewLineForSentry
  val defaultStackIndex = 19
  val defaultSecondStackIndex = 20
  val isPrintln = false
  val isUseSentry = false
}
