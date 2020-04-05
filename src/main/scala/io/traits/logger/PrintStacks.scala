package io.traits.logger

import io.AppLogger
import io.AppLogger.getStackTraceInfoUpToIndex

trait PrintStacks {
  this: AppLogger.type =>

  def printStacks(stackIndex: Int, isPrintStack: Boolean = false): Unit = {
    if (!isPrintStack) {
      return
    }

    val stackIndex2 = stackIndex + 1
    val message = getStackTraceInfoUpToIndex(stackIndex2)
    info(message, stackIndex2)
  }
}
