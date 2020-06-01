package shared.io.extensions.traits.primitiveTypes

import org.joda.time.DateTime
import shared.com.ortb.constants.AppConstants
import shared.io.helpers.{ EmptyValidateHelper, JodaDateTimeHelper }

trait TypeConvertStringToDateTime extends StringExtensionEssentials {
  def toDateTime(dateTimePattern : String = AppConstants.DefaultDateTimeFormatPattern) : DateTime = {
    EmptyValidateHelper.throwOnNullOrNoneOrNil(
      s,
      Some(s"Given string is Empty cannot be converted to Joda DateTime(pattern:$dateTimePattern)"))
    JodaDateTimeHelper.getDateTimeFrom(s, dateTimePattern)
  }

  def toDate(datePattern : String = AppConstants.DefaultDateFormatPattern) : DateTime = {
    EmptyValidateHelper.throwOnNullOrNoneOrNil(
      s,
      Some(s"Given string is Empty cannot be converted to Joda Date(pattern:$datePattern)"))
    JodaDateTimeHelper.getDateTimeFrom(s, datePattern)
  }
}
