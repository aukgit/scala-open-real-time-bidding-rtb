package io

import io.traits.logger._
import io.traits._

object AppLogger extends
  LoggerProperties with
  MethodNameHeaderGetter with
  MultipleStackTracesInfoDisplayGetter with
  PrintStacks with
  ErrorLogger with
  InfoLogger with
  DebugLogger with
  WarnLogger with
  EntitiesLogger {
  def title(): Unit = log.warn(header)
}
