package shared.io.traits.logger

import io.sentry.Sentry
import shared.com.ortb.enumeration.LogLevelType
import shared.io.loggers.AppLogger._

trait InfoLogger {
  this : PrintStacks with MethodNameHeaderGetter =>

  def conditionalInfo(isPrint : Boolean, msg : String, stackIndex : Int = 4, isPrintStack : Boolean = false) : Unit = {
    if (isPrint) {
      info(msg, stackIndex, isPrintStack)
    }
  }

  def info(
    msg                             : String, stackIndex : Int = defaultStackIndex,
    isPrintStack                    : Boolean = false) : Unit = {
    val message = s"${LogLevelType.INFO} : (${getMethodNameHeader(stackIndex)}) - ${msg}"

    additionalLogging(
      message = message,
      logLevelType = LogLevelType.INFO,
      stackIndex = stackIndex,
      isPrintStack = isPrintStack
    )
  }
}
