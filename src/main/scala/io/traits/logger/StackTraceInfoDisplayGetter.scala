package io.traits.logger

trait StackTraceInfoDisplayGetter {
  def getStackTraceInfo(stack : StackTraceElement) : String = {
    s"${stack.getFileName}.${stack.getClassName}.${stack.getMethodName}() [Line: ${stack.getLineNumber}] "
  }
}
