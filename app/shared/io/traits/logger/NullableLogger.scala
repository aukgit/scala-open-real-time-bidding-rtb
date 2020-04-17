package shared.io.traits.logger

import shared.com.ortb.enumeration.LogLevelType
import shared.com.ortb.enumeration.LogLevelType.LogLevelType
import shared.io.logger.AppLogger
import shared.io.traits.FutureToRegular

import scala.concurrent.Future
import scala.util.Try

trait NullableLogger {
  this: AppLogger.type =>

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
}
