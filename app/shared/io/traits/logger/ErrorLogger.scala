package shared.io.traits.logger

import io.sentry.Sentry
import shared.com.ortb.enumeration.LogLevelType
import shared.com.ortb.model.error.FileErrorModel
import shared.io.logger.AppLogger._

trait ErrorLogger {
  def error(msg : String) : Unit = {
    error(msg, 4, isPrintStack = false)
  }

  def error(msg : String, isPrintStack : Boolean) : Unit = {
    error(msg, 4, isPrintStack)
  }

  def error(msg : String, stackIndex : Int, isPrintStack : Boolean) : Unit = {
    val message = s"${LogLevelType.ERROR} : (${getMethodNameHeader(stackIndex)}) - ${msg}"
    additionalLogging(
      message = message,
      logLevelType = LogLevelType.ERROR,
      stackIndex = stackIndex,
      isPrintStack = isPrintStack
    )
  }

  def fileError(
    fileError : FileErrorModel,
    stackIndex : Int = defaultStackIndex,
    isPrintStack : Boolean = false) : Unit = {
    val message = getFileErrorMessage(fileError)
    val finalMessage = s"File ${LogLevelType.ERROR} : (${getMethodNameHeader(stackIndex)}) - ${message}"

    additionalLogging(
      message = finalMessage,
      logLevelType = LogLevelType.ERROR,
      stackIndex = stackIndex,
      isPrintStack = isPrintStack
    )
  }

  def getFileErrorMessage(fileError : FileErrorModel) : String = {
    s"Error Title: ${fileError.title}, ${newLine}FilePath: ${fileError.filePath},${newLine}Cause: ${fileError.cause}," +
      s"${newLine}Content: ${fileError.content}"
  }

  def error(
    exception : Exception,
    additionalMessage : String = null,
    stackIndex        : Int = defaultStackIndex,
    isPrintStack      : Boolean = false
  ) : Unit = {
    val methodNameDisplay = getMethodNameHeader(stackIndex)
    val message = s"Exception Error: ${methodNameDisplay} - ${
      exception
        .toString
    }${newLine}${additionalMessage}"
    val newError = new Error(message)

    additionalLoggingException(
      message = message,
      exception = Some(exception),
      newError = Some(newError),
      logLevelType = LogLevelType.ERROR,
      stackIndex = stackIndex,
      isPrintStack = isPrintStack
    )
  }
}