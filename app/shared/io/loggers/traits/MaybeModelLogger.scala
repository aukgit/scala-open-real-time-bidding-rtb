package shared.io.loggers.traits

import shared.com.ortb.enumeration.LogLevelType
import shared.com.ortb.enumeration.LogLevelType.LogLevelType
import shared.io.loggers.AppLogger

trait MaybeModelLogger {
  this : AppLogger.type =>
  def log[T](
    message : String,
    message2 : String = "",
    maybeAny : Option[Any] = None,
    maybeModels : Option[Iterable[T]] = None,
    isMessagePrint: Boolean = true,
    logLevelType : LogLevelType = LogLevelType.DEBUG,
    stackIndex : Int = defaultSecondStackIndex,
    isPrintStack : Boolean = false) : Unit = {
    try {
      val messageFinal : String = GetCompiledMessage(message, message2, logLevelType, stackIndex)

      if(isMessagePrint){
        additionalLogging(messageFinal, logLevelType, stackIndex, isPrintStack)
      }

      if (maybeAny.isDefined) {
        logMaybeItem(message = messageFinal, maybeAny, logLevelType, stackIndex, isPrintStack = false)
      }

      val isExecute = maybeModels.isDefined && maybeModels.get.nonEmpty
      logEntitiesWithCondition(isExecute = isExecute, maybeModels, messageFinal)
    } catch {
      case e : Exception => println(e.toString)
    }
  }
}
