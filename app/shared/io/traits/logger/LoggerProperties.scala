package shared.io.traits.logger

import shared.com.ortb.constants.AppConstants

trait LoggerProperties {
  val header = "[Open RTB]"
  val newLine : String = AppConstants.NewLineForSentry
  val defaultStackIndex = 3
  val isPrintln = false
  val isUseSentry = false
}
