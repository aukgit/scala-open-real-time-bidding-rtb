package shared.io.loggers.traits

import shared.com.ortb.constants.AppConstants

trait MethodNameHeaderGetter extends
  StackTraceInfoDisplayGetter {
  def getMethodNameHeader(stackIndex : Int) : String = {
    val stacks    = Thread.currentThread().getStackTrace
    val len       = stacks.length
    val lastIndex = len - 2

    if (lastIndex <= stackIndex) {
      return getStackTraceInfo(stacks(stackIndex)) + getStackTraceInfo(stacks(lastIndex))
    }

    if (stacks.nonEmpty) {
      return getStackTraceInfo(stacks(lastIndex))
    }

    ""
  }

  def getMethodNameHeaderForIndexes(
    indexes : Int*
  ) : String = {
    indexes.map(index => getMethodNameHeader(index))
           .mkString(AppConstants.HyphenRightAngel)
  }
}
