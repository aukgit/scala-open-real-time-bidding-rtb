package io.traits.logger

import io.AppLogger.log
import io.sentry.Sentry

trait WarnLogger {
  this: PrintStacks with MethodNameHeaderGetter =>

  def warn(msg: String, stackIndex: Int = 3, isPrintStack: Boolean = false): Unit = {
    val message = s"WARN : (${getMethodNameHeader(stackIndex)}) - ${msg}"
    Sentry.getContext.addTag("level", "warn")
    log.warn(message)
    Sentry.capture(message)
    printStacks(isPrintStack)
  }
}
