package shared.io.loggers.traits

import shared.com.ortb.enumeration.LogLevelType
import shared.io.loggers.AppLogger.{ additionalLogging, defaultStackIndex }

trait WarnLogger {
  this : PrintStacks with MethodNameHeaderGetter =>

  def warn(msg : String, stackIndex : Int = defaultStackIndex, isPrintStack : Boolean = false) : Unit = {
    val methodNameDisplay = getMethodNameDisplayWrapper(stackIndex)
    val message = s"WARN :$methodNameDisplay ${ msg }"

    additionalLogging(
      message = message,
      logLevelType = LogLevelType.WARN,
      stackIndex = stackIndex,
      isPrintStack = isPrintStack
    )
  }
}
