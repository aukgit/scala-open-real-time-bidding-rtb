package io.traits.logger

import io.AppLogger
import io.AppLogger.getStackTraceInfoUpToIndex

trait PrintStacks {
  this: AppLogger.type =>

  def printStacks(isPrintStack: Boolean = false): Unit = {
    if (!isPrintStack) {
      return
    }

    val message = getStackTraceInfoUpToIndex
    this.info(message)
  }
}
