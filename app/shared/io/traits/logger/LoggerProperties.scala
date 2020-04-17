package shared.io.traits.logger

import org.slf4j
import org.slf4j.impl.StaticLoggerBinder
import shared.com.ortb.constants.AppConstants

trait LoggerProperties {
  val header = "[Open RTB]"
  val newLine : String = AppConstants.NewLineForSentry
  val defaultStackIndex = 3
  val isPrintln = false
  val isUseSentry = false
  val innerLogger : slf4j.Logger = StaticLoggerBinder
    .getSingleton
    .getLoggerFactory
    .getLogger("ROOT")
}
