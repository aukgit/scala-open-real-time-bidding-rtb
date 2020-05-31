package shared.io.extensions.traits.primitiveTypes

import io.circe.generic.decoding.DerivedDecoder
import io.circe.generic.encoding.DerivedAsObjectEncoder
import org.joda.time.DateTime
import shapeless.Lazy
import shared.com.ortb.constants.AppConstants
import shared.io.helpers.{ EmptyValidateHelper, JodaDateTimeHelper }
import shared.io.jsonParse.implementations.BasicJsonEncoderImplementation

trait TypeConvertString {
  protected val s : String
  lazy val hasCharacter : Boolean = EmptyValidateHelper.isStringDefined(s)
  lazy val isStringEmpty : Boolean = !hasCharacter
  lazy val toBoolString : String = toBoolean.toString
  lazy val toBoolean : Boolean = if (isTrue) true else false
  lazy private val isTrueString = hasCharacter && s.equalsIgnoreCase("true")

  lazy private val isTrue =
    isTrueString ||
      s == "1" ||
      isYesString
  lazy private val isYesString = hasCharacter && s.equalsIgnoreCase("yes")

  def toIntOrDefault(default : Int = 0) : Int = {
    if (isStringEmpty) {
      return default
    }

    val intResult = s.toIntOption
    intResult.getOrElse(default)
  }

  /**
   * On empty string returns orElseString
   *
   * @param orElse : On empty string returns orElseString
   *
   * @return
   */
  def getOrElseDefault(orElse : String = "") : String = {
    getIfExist(s, orElse)
  }

  /**
   *
   * @return String
   */
  def getIfExist(onExist : String = s, onNonExist : String = "") : String = {
    if (isStringEmpty) {
      return onNonExist
    }

    onExist
  }

  def toDateTime(dateTimePattern : String = AppConstants.DefaultDateTimeFormatPattern) : DateTime = {
    EmptyValidateHelper.throwOnNullOrNoneOrNil(s, Some(s"Given string is Empty cannot be converted to Joda DateTime(pattern:$dateTimePattern)"))
    JodaDateTimeHelper.getDateTimeFrom(s, dateTimePattern)
  }

  def toDate(datePattern : String = AppConstants.DefaultDateFormatPattern) : DateTime = {
    EmptyValidateHelper.throwOnNullOrNoneOrNil(s, Some(s"Given string is Empty cannot be converted to Joda Date(pattern:$datePattern)"))
    JodaDateTimeHelper.getDateTimeFrom(s, datePattern)
  }

  lazy val getLines : Array[String] = s.split(AppConstants.NewLine)

  def getLinesWithLineNumbers(lineJoiner : String = " . ") : Array[String] = {
    var lineNumber = 0
    getLines.map(line => {
      lineNumber += 1
      s"$lineNumber$lineJoiner$line"
    })
  }

  /**
   * Parse a json to object
   *
   * @param decoder
   * @param encoder
   * @tparam T2
   *
   * @return
   */
  def asFromJson[T2](
                      implicit decoder : Lazy[DerivedDecoder[T2]],
                      encoder : Lazy[DerivedAsObjectEncoder[T2]]) : T2 = {
    val basicEncoder = new BasicJsonEncoderImplementation[T2]()(decoder, encoder)
    basicEncoder.genericJsonParser.toModelDirect(s)
  }

  def asObjectsFromJson[T2](
                             implicit decoder : Lazy[DerivedDecoder[T2]],
                             encoder : Lazy[DerivedAsObjectEncoder[T2]]) : Iterable[T2] = {
    val basicEncoder = new BasicJsonEncoderImplementation[T2]()(decoder, encoder)
    basicEncoder.genericJsonParser.toModelsDirect(s)
  }

  def asObjectFromJson[T2](
                            implicit decoder : Lazy[DerivedDecoder[T2]],
                            encoder : Lazy[DerivedAsObjectEncoder[T2]]) : T2 = {
    val basicEncoder = new BasicJsonEncoderImplementation[T2]()(decoder, encoder)
    basicEncoder.genericJsonParser.toModelDirect(s)
  }
}



