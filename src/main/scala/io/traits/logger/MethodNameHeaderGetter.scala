package io.traits.logger

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
}