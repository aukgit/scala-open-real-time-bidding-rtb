package shared.io.logger

import org.slf4j
import org.slf4j.{LoggerFactory, Marker}
import play.api.{Logger, Logging}
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
  AdditionalLogger  {
  // val playLogger = new Logger(innerLogger)
  val marker: Marker             = MarkerFactory.getMarker("SOMEMARKER")
  implicit val mc: MarkerContext = MarkerContext(marker)
  val log : slf4j.Logger = LoggerFactory.getLogger(this.getClass)
}

