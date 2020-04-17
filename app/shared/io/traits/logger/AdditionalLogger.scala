package shared.io.traits.logger

import com.sun.org.apache.xerces.internal.impl.dv.InvalidDatatypeValueException
import shared.com.ortb.enumeration.LogLevelType
import shared.com.ortb.enumeration.LogLevelType.LogLevelType
import shared.io.logger.AppLogger
import shared.io.traits.FutureToRegular

import scala.concurrent.Future
import scala.util.Try

trait AdditionalLogger {
  this : AppLogger.type =>

  def additionalLogging(
    message : String,
    logLevelType : LogLevelType,
    stackIndex   : Int = 6,
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

    log.info(message)
    log.debug(message)
    innerLogger.info(message)

    println(innerLogger.isDebugEnabled)

    logLevelType match {
      case LogLevelType.ALL | LogLevelType.INFO =>
        log.info(message)
        log.info(message)
      case LogLevelType.DEBUG =>
        logger.debug(message)
        innerLogger.debug(message)
      case LogLevelType.WARN =>
        log.warn(message)
      case LogLevelType.ERROR | LogLevelType.FATAL =>
        log.error(message)
      case _ =>
        throw new InvalidDatatypeValueException("LogType is out of range", Array(logLevelType))
    }
  }

  def logFutureNullable[T](
    message : String,
    nullableObject : Future[Option[T]],
    logLevelType : LogLevelType = LogLevelType.DEBUG,
    stackIndex : Int = 5,
    isPrintStack : Boolean = false) : Unit = {
    val regularNullable = Try(FutureToRegular.toRegular(nullableObject)).get
    logNonFutureNullable(
      message = message,
      nullableObject = regularNullable,
      logLevelType = logLevelType,
      stackIndex = stackIndex,
      isPrintStack = isPrintStack)
  }

  def logNonFutureNullable[T](
    message : String,
    nullableObject : Option[T],
    logLevelType : LogLevelType = LogLevelType.DEBUG,
    stackIndex : Int = 4,
    isPrintStack : Boolean = false) : Unit = {
    val methodNameDisplay = getMethodNameHeader(stackIndex)
    val finalMessage = s"[${logLevelType}] : ${methodNameDisplay} - $message"
    if (nullableObject.isEmpty) {
      additionalLogging(
        message = s"""$finalMessage : null.""",
        logLevelType = logLevelType,
        stackIndex = stackIndex,
        isPrintStack = isPrintStack)

      return
    }

    try {
      additionalLogging(
        message = s"${finalMessage} : ${nullableObject.get.toString}.",
        logLevelType = logLevelType,
        stackIndex = stackIndex,
        isPrintStack = isPrintStack)
    } catch {
      case e : Exception => AppLogger.error(e)
    }
  }

  def additionalLoggingException(
    message : String,
    exception : Option[Exception],
    newError : Option[Error],
    logLevelType : LogLevelType = LogLevelType.ERROR,
    stackIndex : Int = 3,
    isPrintStack : Boolean = false) : Unit = {
    try {
      log.error(message, exception.get)
      logBasedOnLevel(message, logLevelType)
      exceptionLogToSentry(message, exception, logLevelType)
      errorLogToSentry(newError)
      printStacks(isPrintStack)
    } catch {
      case e : Exception => println(e.toString)
    }
  }
}
