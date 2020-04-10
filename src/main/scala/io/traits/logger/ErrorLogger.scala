package io.traits.logger

import com.ortb.model.error.FileErrorModel
import io.AppLogger
import io.AppLogger.{printStacks, getMethodNameHeader, newLine, error, log}
import io.sentry.Sentry

trait ErrorLogger {
  def logErrorLevel(): Unit = Sentry.getContext.addTag("level", "error")

  def error(msg: String): Unit = {
    error(msg, 4, isPrintStack = false)
  }

  def error(msg: String, isPrintStack: Boolean): Unit = {
    error(msg, 4, isPrintStack)
  }

  def error(msg: String, stackIndex: Int, isPrintStack: Boolean): Unit = {
    val message = s"Error : (${getMethodNameHeader(stackIndex)}) - ${msg}"
    logErrorLevel()
    log.error(message)
    Sentry.capture(message)
    printStacks(isPrintStack)
  }

  def getFileErrorMessage(fileError: FileErrorModel): String = {
    s"Error Title: ${fileError.title}, ${newLine}FilePath: ${fileError.filePath},${newLine}Cause: ${fileError.cause},${newLine}Content: ${fileError.content}"
  }

  def fileError(fileError: FileErrorModel, stackIndex: Int = 3, isPrintStack: Boolean = false): Unit = {
    val message = getFileErrorMessage(fileError)
    val finalMessage = s"File Error : (${getMethodNameHeader(stackIndex)}) - ${message}"
    logErrorLevel()
    log.error(finalMessage)
    Sentry.capture(finalMessage)
    printStacks(isPrintStack)
  }

  def error(
    exception: Exception,
    additionalMessage: String = null,
    stackIndex: Int = 3,
    isPrintStack: Boolean = false
  ): Unit = {
    val methodNameDisplay = getMethodNameHeader(stackIndex)
    val message = s"Exception Error: ${methodNameDisplay} - ${exception.toString}${newLine}${additionalMessage}"
    log.error(message)
    val newError = new Error(message)
    Sentry.capture(newError) // combine error log
    log.error(exception.toString) // keep the actual error log as is.
    Sentry.capture(exception) // keep the actual error log as is.
    printStacks(isPrintStack)
  }
}