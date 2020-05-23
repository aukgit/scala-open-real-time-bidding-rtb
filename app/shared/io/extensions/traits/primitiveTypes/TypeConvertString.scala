package shared.io.extensions.traits.primitiveTypes

import org.joda.time.DateTime
import shared.com.ortb.constants.AppConstants
import shared.io.helpers.{ EmptyValidateHelper, JodaDateTimeHelper }

trait TypeConvertString {
  lazy val hasCharacter : Boolean = EmptyValidateHelper.isStringDefined(s)
  lazy val isEmpty : Boolean = !hasCharacter
  lazy val toBoolString : String = toBoolean.toString
  lazy val toBoolean : Boolean = if (isTrue) true else false
  lazy private val isTrueString = hasCharacter && s.equalsIgnoreCase("true")

  lazy private val isTrue =
    isTrueString ||
      s == "1" ||
      isYesString
  lazy private val isYesString = hasCharacter && s.equalsIgnoreCase("yes")
  protected val s : String

  def toDateTime(dateTimePattern : String = AppConstants.DefaultDateTimeFormatPattern) : DateTime = {
    EmptyValidateHelper.throwOnNullOrNoneOrNil(s, Some(s"Given string is Empty cannot be converted to Joda DateTime(pattern:$dateTimePattern)"))
    JodaDateTimeHelper.getDateTimeFrom(s, dateTimePattern)
  }

  def toDate(datePattern : String = AppConstants.DefaultDateFormatPattern) : DateTime = {
    EmptyValidateHelper.throwOnNullOrNoneOrNil(s, Some(s"Given string is Empty cannot be converted to Joda Date(pattern:$datePattern)"))
    JodaDateTimeHelper.getDateTimeFrom(s, datePattern)
  }
}

