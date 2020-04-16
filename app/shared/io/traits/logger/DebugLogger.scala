package shared.io.traits.logger

import io.sentry.Sentry
import shared.io.AppLogger._

trait DebugLogger {
  def debug(msg : String, stackIndex : Int = 3, isPrintStack : Boolean = false) : Unit = {
    val message = s"DEBUG : (${getMethodNameHeader(stackIndex)}) - ${msg}"
    Sentry.getContext.addTag("level", "debug")
    log.debug(message)
    Sentry.capture(message)
    printStacks(isPrintStack)
  }
}
