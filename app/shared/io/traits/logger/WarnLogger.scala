package shared.io.traits.logger

import shared.io.loggers.AppLogger._
import io.sentry.Sentry
import shared.com.ortb.enumeration.LogLevelType

trait WarnLogger {
  this : PrintStacks with MethodNameHeaderGetter =>

  def warn(msg : String, stackIndex : Int = defaultStackIndex, isPrintStack : Boolean = false) : Unit = {
    val message = s"WARN : (${getMethodNameHeader(stackIndex)}) - ${msg}"

    additionalLogging(
      message = message,
      logLevelType = LogLevelType.WARN,
      stackIndex = stackIndex,
      isPrintStack = isPrintStack
    )
  }
}
