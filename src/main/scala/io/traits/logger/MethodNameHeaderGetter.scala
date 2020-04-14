package io.traits.logger

trait MethodNameHeaderGetter extends
  StackTraceInfoDisplayGetter {
  def getMethodNameHeader(stackIndex : Int) : String = {
    val stacks    = Thread.currentThread().getStackTrace
    val len       = stacks.length
    val lastIndex = len - 1
    if (lastIndex <= stackIndex) {
      return getStackTraceInfo(stacks(stackIndex))
    }
    else if (stacks.nonEmpty) {
      return getStackTraceInfo(stacks(lastIndex))
    }

    ""
  }
}