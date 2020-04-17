package shared.io.logger

import play.api.Logging
import shared.io.traits.logger.{EntitiesLogger, _}

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
  EntitiesLogger with
  SentryLogger with
  AdditionalLogger {}

