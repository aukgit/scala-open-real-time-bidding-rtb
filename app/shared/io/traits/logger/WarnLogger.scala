package shared.io.traits.logger

import shared.io.logger.AppLogger._
import io.sentry.Sentry

trait WarnLogger {
  this : PrintStacks with MethodNameHeaderGetter =>

  def warn(msg : String, stackIndex : Int = 3, isPrintStack : Boolean = false) : Unit = {
    val message = s"WARN : (${getMethodNameHeader(stackIndex)}) - ${msg}"
    Sentry.getContext.addTag("level", "warn")
    log.warn(message)
    Sentry.capture(message)
    printStacks(isPrintStack)
  }
}
