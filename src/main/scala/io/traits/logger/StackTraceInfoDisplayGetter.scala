package io.traits.logger

trait StackTraceInfoDisplayGetter {
  def getStackTraceInfo(stack : StackTraceElement) : String = {
    s"${stack.getClass.getTypeName}.${stack.getMethodName}() [Line: ${stack.getLineNumber}] "
  }
}
