package io

import io.traits.logger._

object AppLogger extends
  LoggerProperties with
  MethodNameHeaderGetter with
  MultipleStackTracesInfoDisplayGetter with
  PrintStacks with
  ErrorLogger with
  InfoLogger with
  DebugLogger with
  WarnLogger {
  def title(): Unit = log.warn(header)
}
