package shared.io.traits.logger

import shared.com.ortb.enumeration.LogLevelType
import shared.io.loggers.AppLogger._

trait WarnLogger {
  this : PrintStacks with MethodNameHeaderGetter =>

  def warn(msg : String, stackIndex : Int = defaultStackIndex, isPrintStack : Boolean = false) : Unit = {
    val message = s"WARN : (${getMethodNameHeaderForIndexes(stackIndex, stackIndex + 1)}) - ${msg}"

    additionalLogging(
      message = message,
      logLevelType = LogLevelType.WARN,
      stackIndex = stackIndex,
      isPrintStack = isPrintStack
    )
  }
}
