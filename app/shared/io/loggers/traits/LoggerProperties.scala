package shared.io.loggers.traits

import shared.com.ortb.constants.AppConstants

trait LoggerProperties {
  val header = "[Open RTB]"
  val newLine : String = AppConstants.NewLineForSentry
  val defaultStackIndex = 3
  val defaultSecondStackIndex = 4
  val isPrintln = false
  val isPrintMethodName = false
  val isUseSentry = false
}
