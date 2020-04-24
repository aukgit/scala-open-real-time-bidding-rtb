package shared.io.loggers.traits

import shared.com.ortb.enumeration.LogLevelType
import shared.io.loggers.AppLogger.{ additionalLogging, defaultStackIndex, getMethodNameDisplayWrapper }

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
    val methodNameDisplay = getMethodNameDisplayWrapper(stackIndex)
    val message = s"${LogLevelType.INFO} :$methodNameDisplay ${msg}"

    additionalLogging(
      message = message,
      logLevelType = LogLevelType.INFO,
      stackIndex = stackIndex,
      isPrintStack = isPrintStack
    )
  }
}
