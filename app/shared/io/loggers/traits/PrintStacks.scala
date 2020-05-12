package shared.io.loggers.traits

import shared.com.ortb.enumeration.LogLevelType
import shared.com.ortb.enumeration.LogLevelType.LogLevelType
import shared.io.loggers.AppLogger

trait PrintStacks {
  this : AppLogger.type =>

  def printStacks(
    isPrintStack : Boolean = false,
    logLevelType : LogLevelType = LogLevelType.INFO) : Unit = {
    if (!isPrintStack) {
      return
    }

    val message = getStackTraceInfoUpToIndex
    additionalLogging(message, logLevelType)
  }
}
