package shared.io.traits.logger

import io.sentry.Sentry
import shared.io.AppLogger._

trait InfoLogger {
  this : PrintStacks with MethodNameHeaderGetter =>

  def conditionalInfo(isPrint : Boolean, msg : String, stackIndex : Int = 4, isPrintStack : Boolean = false) : Unit = {
    if (isPrint) {
      info(msg, stackIndex, isPrintStack)
    }
  }

  def info(msg : String, stackIndex : Int = 3, isPrintStack : Boolean = false) : Unit = {
    val message = s"INFO : (${getMethodNameHeader(stackIndex)}) - ${msg}"
    log.info(message)
    println(message)
    Sentry.getContext.addTag("level", "info")
    Sentry.capture(message)
    printStacks(isPrintStack)
  }
}
