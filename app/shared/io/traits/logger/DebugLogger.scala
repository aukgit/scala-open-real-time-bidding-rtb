package shared.io.traits.logger

import io.sentry.Sentry
import shared.io.logger.AppLogger._

trait DebugLogger {
  def debug(msg1 : String, msg2 : String = "", stackIndex : Int = 3, isPrintStack : Boolean = false) : Unit = {
    val msgCompiled = if(msg2.isEmpty) msg1 else s"${msg1} - ${msg2}"
    val message = s"DEBUG : (${getMethodNameHeader(stackIndex)}) - ${msgCompiled}"
    Sentry.getContext.addTag("level", "debug")
    log.debug(message)
    Sentry.capture(message)
    printStacks(isPrintStack)
  }
}
