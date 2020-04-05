package io.traits.logger

import com.ortb.general.AppConstants

trait MethodNameHeaderGetter extends StackTraceInfoDisplayGetter {
  def getMethodNameHeader(stackIndex: Int): String = {
    val stack: StackTraceElement = Thread.currentThread().getStackTrace()(stackIndex)
    getStackTraceInfo(stack)
  }
}