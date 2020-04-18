package shared.io.traits.logger

import com.sun.org.apache.xerces.internal.impl.dv.InvalidDatatypeValueException
import shared.com.ortb.enumeration.LogLevelType
import shared.com.ortb.enumeration.LogLevelType.LogLevelType
import shared.io.loggers.AppLogger

trait AdditionalLogger {
  this : AppLogger.type =>

  def additionalLogging(
    message : String,
    logLevelType : LogLevelType,
    stackIndex   : Int = defaultSecondStackIndex,
    isPrintStack : Boolean = false) : Unit = {
    try {
      logBasedOnLevel(message, logLevelType)
      logToSentry(message, logLevelType)
      printStacks(isPrintStack, logLevelType)
    } catch {
      case e : Exception => println(e.toString)
    }
  }

  private def logBasedOnLevel(
    message      : String,
    logLevelType : LogLevelType) : Unit = {
    if (isPrintln) {
      println(message)
    }

    logLevelType match {
      case LogLevelType.ALL | LogLevelType.INFO =>
        logger.info(message)
      case LogLevelType.DEBUG =>
        logger.debug(message)
      case LogLevelType.WARN =>
        logger.warn(message)
      case LogLevelType.ERROR | LogLevelType.FATAL =>
        logger.error(message)
      case _ =>
        throw new InvalidDatatypeValueException("LogType is out of range", Array(logLevelType))
    }
  }

  def additionalLoggingException(
    message : String,
    exception : Option[Exception],
    newError : Option[Error],
    logLevelType : LogLevelType = LogLevelType.ERROR,
    stackIndex : Int = defaultSecondStackIndex + 1,
    isPrintStack : Boolean = false) : Unit = {
    try {
      logger.error(message, exception.get)
      logBasedOnLevel(message, logLevelType)
      exceptionLogToSentry(message, exception, logLevelType)
      errorLogToSentry(newError)
      printStacks(isPrintStack)
    } catch {
      case e : Exception => println(e.toString)
    }
  }
}
