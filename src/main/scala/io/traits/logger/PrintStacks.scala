package io.traits.logger

import io.AppLogger
import io.AppLogger.getStackTraceInfoUpToIndex

trait PrintStacks {
  this: AppLogger.type =>

  def printStacks(stackIndex: Int, isPrintStack: Boolean = false): Unit = {
    if (!isPrintStack) {
      return
    }

    val message = getStackTraceInfoUpToIndex
    this.info(message)
  }
}
