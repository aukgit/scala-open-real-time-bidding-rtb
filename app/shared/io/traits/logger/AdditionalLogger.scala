package shared.io.traits.logger

import com.sun.org.apache.xerces.internal.impl.dv.InvalidDatatypeValueException
import shared.com.ortb.enumeration.LogLevelType
import shared.com.ortb.enumeration.LogLevelType.LogLevelType
import shared.io.logger.AppLogger

trait AdditionalLogger {
  this : AppLogger.type =>

  def additionalLogging(
    message      : String,
    logLevelType : LogLevelType,
    stackIndex   : Int = 3,
    isPrintStack : Boolean = false) {
    logBasedOnLevel(message, logLevelType)
    logToSentry(message, logLevelType)
    printStacks(isPrintStack, logLevelType)
  }

  private def logBasedOnLevel(
    message : String,
    logLevelType : LogLevelType) : Unit = {
    if(isPrintln){
      println(message)
    }

    log.info(message)

    logLevelType match {
      case LogLevelType.ALL | LogLevelType.INFO =>
        logger.info(message)
        innerLogger.info(message)
      case LogLevelType.DEBUG =>
        logger.debug(message)
        innerLogger.debug(message)
      case LogLevelType.WARN =>
        logger.warn(message)
        innerLogger.warn(message)
      case LogLevelType.ERROR | LogLevelType.FATAL =>
        logger.error(message)
        innerLogger.error(message)
      case _ =>
        throw new InvalidDatatypeValueException("LogType is out of range", Array(logLevelType))
    }
  }

  def additionalLoggingException(
    message      : String,
    exception    : Option[Exception],
    newError     : Option[Error],
    logLevelType : LogLevelType = LogLevelType.ERROR,
    stackIndex : Int = 3,
    isPrintStack : Boolean = false) {
    logger.error(message, exception.get)
    logBasedOnLevel(message, logLevelType)
    exceptionLogToSentry(message, exception, logLevelType)
    errorLogToSentry(newError)
    printStacks(isPrintStack)
  }
}
