package shared.io.traits.logger

import io.sentry.Sentry
import shared.com.ortb.enumeration.LogLevelType.LogLevelType
import shared.io.logger.AppLogger

trait SentryLogger {
  this : AppLogger.type =>

  def logToSentry(message : String, logLevelType : LogLevelType) : Unit = {
    if (!isUseSentry) {
      return
    }

    Sentry.getContext.addTag("level", logLevelType.toString)
    Sentry.capture(message)
  }

  def exceptionLogToSentry(
    message      : String,
    exception    : Option[Exception],
    logLevelType : LogLevelType) : Unit = {
    if (!isUseSentry) {
      return
    }

    Sentry.getContext.addTag("level", logLevelType.toString)
    Sentry.capture(message)

    if (exception.isDefined) {
      Sentry.capture(exception.get)
    }
  }

  def errorLogToSentry(error : Option[Error]) : Unit = {
    if (!isUseSentry || error.isEmpty) {
      return
    }

    Sentry.capture(error.get)
  }
}
