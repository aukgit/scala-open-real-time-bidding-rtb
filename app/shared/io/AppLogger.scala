package io

import io.traits._
import io.traits.logger._
import slick.util.Logging

object AppLogger extends
  LoggerProperties with
  MethodNameHeaderGetter with
  MultipleStackTracesInfoDisplayGetter with
  PrintStacks with
  ErrorLogger with
  InfoLogger with
  DebugLogger with
  WarnLogger with
  EntitiesLogger with
  Logging {
  def title() : Unit = log.warn(header)
}
