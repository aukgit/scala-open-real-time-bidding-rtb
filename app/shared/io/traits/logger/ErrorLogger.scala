package shared.io.traits.logger

import shared.com.ortb.model.error.FileErrorModel
import shared.io.logger.AppLogger._
import io.sentry.Sentry

trait ErrorLogger {
  def error(msg : String) : Unit = {
    error(msg, 4, isPrintStack = false)
  }

  def error(msg : String, isPrintStack : Boolean) : Unit = {
    error(msg, 4, isPrintStack)
  }

  def error(msg : String, stackIndex : Int, isPrintStack : Boolean) : Unit = {
    val message = s"Error : (${getMethodNameHeader(stackIndex)}) - ${msg}"
    logErrorLevel()
    log.error(message)
    println(message)
    Sentry.capture(message)
    printStacks(isPrintStack)
  }

  def fileError(fileError : FileErrorModel, stackIndex : Int = 3, isPrintStack : Boolean = false) : Unit = {
    val message      = getFileErrorMessage(fileError)
    val finalMessage = s"File Error : (${getMethodNameHeader(stackIndex)}) - ${message}"
    logErrorLevel()
    log.error(finalMessage)
    println(finalMessage)
    Sentry.capture(finalMessage)
    printStacks(isPrintStack)
  }

  def logErrorLevel() : Unit = Sentry.getContext.addTag("level", "error")

  def getFileErrorMessage(fileError : FileErrorModel) : String = {
    s"Error Title: ${fileError.title}, ${newLine}FilePath: ${fileError.filePath},${newLine}Cause: ${fileError.cause}," +
      s"${newLine}Content: ${fileError.content}"
  }

  def error(
    exception         : Exception,
    additionalMessage : String = null,
    stackIndex        : Int = 3,
    isPrintStack      : Boolean = false
  ) : Unit = {
    val methodNameDisplay = getMethodNameHeader(stackIndex)
    val message           = s"Exception Error: ${methodNameDisplay} - ${exception
      .toString}${newLine}${additionalMessage}"
    log.error(message)
    println(message)
    val newError = new Error(message)
    Sentry.capture(newError) // combine error log
    log.error(exception.toString) // keep the actual error log as is.
    Sentry.capture(exception) // keep the actual error log as is.
    printStacks(isPrintStack)
  }
}
