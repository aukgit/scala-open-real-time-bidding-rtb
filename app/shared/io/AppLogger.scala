package shared.io

import io.traits.logger._
import io.traits._
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
