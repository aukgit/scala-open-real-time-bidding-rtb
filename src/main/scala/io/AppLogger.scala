package io

import io.traits.logger._
import io.traits.printResultsLogger

object AppLogger extends
  LoggerProperties with
  MethodNameHeaderGetter with
  MultipleStackTracesInfoDisplayGetter with
  PrintStacks with
  ErrorLogger with
  InfoLogger with
  DebugLogger with
  WarnLogger with
  printResultsLogger {
  def title(): Unit = log.warn(header)
}
