package shared.io.loggers

import play.api.Logging
import shared.io.loggers.traits._

import scala.concurrent.ExecutionContext

object AppLogger extends
  Logging with
  LoggerProperties with
  MethodNameHeaderGetter with
  MultipleStackTracesInfoDisplayGetter with
  PrintStacks with
  ErrorLogger with
  InfoLogger with
  DebugLogger with
  WarnLogger with
  JsonLogger with
  MaybeModelLogger with
  EntitiesLogger with
  SentryLogger with
  AdditionalLogger with
  NullableLogger with
  LoggerSelfPrinter {

  def loggerStatus() : Unit = {
    printLoggerStatus(logger, "logger")
  }

  implicit def getLoggerExecutionContext : ExecutionContext = executionContextManager.defaultExecutionContext
}

