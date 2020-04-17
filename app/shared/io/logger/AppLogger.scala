package shared.io.logger

import play.api.Logger
import shared.io.traits.logger.{EntitiesLogger, _}

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
  SentryLogger with
  AdditionalLogger {
  val playLogger = new Logger(new ch.qos.logback.classic.Logger)
}

