package shared.io.traits.logger

import shared.io.AppLogger

trait PrintStacks {
  this : AppLogger.type =>

  def printStacks(isPrintStack : Boolean = false) : Unit = {
    if (!isPrintStack) {
      return
    }

    val message = getStackTraceInfoUpToIndex
    this.info(message)
  }
}
