package shared.io.loggers.traits

import shared.com.ortb.enumeration.LogLevelType
import shared.io.loggers.AppLogger.{ additionalLogging, defaultStackIndex }

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
    val message = s"${LogLevelType.INFO} : (${getMethodNameHeaderForIndexes(stackIndex, stackIndex + 1)}) - ${msg}"

    additionalLogging(
      message = message,
      logLevelType = LogLevelType.INFO,
      stackIndex = stackIndex,
      isPrintStack = isPrintStack
    )
  }
}
