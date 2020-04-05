package io.traits.logger

import com.ortb.general.AppConstants

trait MultipleStackTracesInfoDisplayGetter extends StackTraceInfoDisplayGetter {
  def getStackTraceInfoUpToIndex(stackIndex: Int): String = {
    val stacks = Thread.currentThread().getStackTrace
    val limit = stackIndex + 1
    val len = if (limit >= stacks.length) stacks.length else limit
    val items = new Array[String](len)

    for(i <- 0 until len){
      val stack = stacks(i)
      getStackTraceInfo(stack)
    }

    items.mkString(AppConstants.NewLineForSentry)
  }
}
