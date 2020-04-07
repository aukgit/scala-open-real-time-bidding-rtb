package io.traits.logger

import io.sentry.Sentry
import io.AppLogger.log

trait InfoLogger {
  this: PrintStacks with MethodNameHeaderGetter =>

  def info(msg: String, stackIndex: Int = 3, isPrintStack: Boolean = false): Unit = {
    val message = s"INFO : (${getMethodNameHeader(stackIndex)}) - ${msg}"
    log.info(message)
    Sentry.getContext.addTag("level", "info")
    Sentry.capture(message)
    printStacks(isPrintStack)
  }
}
