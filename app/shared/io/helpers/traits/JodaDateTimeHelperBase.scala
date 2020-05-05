package shared.io.helpers.traits

import java.util.Date

import org.joda.time.format.DateTimeFormat
import org.joda.time.{ DateTime, DateTimeZone }
import shared.com.ortb.constants.AppConstants
import shared.io.helpers.{ EmptyValidateHelper, NumberHelper }
import shared.io.loggers.AppLogger

trait JodaDateTimeHelperBase {
  lazy val defaultDateTimeFormatPattern = "MM/dd/yyyy HH:mm:ss"
  lazy val defaultDateFormatPattern = "MM/dd/yyyy"

  implicit def nowUtc : DateTime = DateTime.now(DateTimeZone.UTC)

  def nowUtcAsString(pattern : String = defaultDateTimeFormatPattern) : String = {
    try {
      return nowUtc.toString(pattern)
    } catch {
      case e : Exception =>
        AppLogger.errorCaptureAndThrow(
          e,
          s"Cannot convert pattern(${AppConstants.Quote}${ pattern }${AppConstants.Quote}) to String.")
    }

    null
  }

  def toStringWithPattern(dateTime: DateTime, pattern : String = defaultDateTimeFormatPattern) : String = {
    try {
      return dateTime.toString(pattern)
    } catch {
      case e : Exception =>
        AppLogger.errorCaptureAndThrow(
          e,
          s"Cannot convert pattern(${AppConstants.Quote}${ pattern }${AppConstants.Quote}) to String.")
    }

    null
  }

  def getDateTimeFrom(
    inputDateAsString : String,
    pattern : String = defaultDateTimeFormatPattern) : DateTime = {
    try {
      val dtf = DateTimeFormat.forPattern(pattern)
      return DateTime.parse(inputDateAsString, dtf)
    } catch {
      case e : Exception =>
        AppLogger.errorCaptureAndThrow(
          e,
          s"Cannot parse datetime(${AppConstants.Quote}$inputDateAsString${AppConstants.Quote}) using pattern(${AppConstants.Quote}${ pattern }${AppConstants.Quote}).")
    }

    null
  }

  def todayDate : Date = new Date

  def nowLocal : DateTime = DateTime.now

  def nowUtcMillis : Long = nowUtc.getMillis

  def millisToDateTime(millis : Long) : DateTime = new DateTime(millis)

  def millisToUtcDateTime(millis : Long) : DateTime = new DateTime(millis, DateTimeZone.UTC)

  def getDifferenceBetween(earlier: DateTime ,later:DateTime ) : DateTime =
    later.minus( earlier.getMillis);

  def getDaysDifferenceBetween(earlier: DateTime ,later:DateTime ) : Int =
    getDifferenceBetween(earlier, later).getDayOfYear - 1

  def millisStringToDateTime(millis : String) : DateTime = {
    EmptyValidateHelper.throwOnNullOrNone(millis)
    val asLong = NumberHelper.isLong(millis)

    if (asLong.isSuccess) {
      return millisToDateTime(asLong.result.get)
    }

    null
  }
}
