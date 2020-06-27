package shared.io.extensions.traits.primitiveTypes

import shared.com.ortb.constants.AppConstants

trait TypeConvertString
  extends StringExtensionForExistence
    with TypeConvertStringJson
    with TypeConvertStringToDateTime {

  lazy val toBoolString : String = toBoolean.toString
  lazy val toBoolean : Boolean = if (isTrue) true else false
  lazy val getLines : Array[String] = s.split(AppConstants.NewLine)
  lazy private val isTrueString =
    hasCharacter &&
      s.equalsIgnoreCase(AppConstants.BooleanTrueString)
  lazy private val isTrue =
    isTrueString ||
      s == "1" ||
      isYesString
  lazy private val isYesString =
    hasCharacter &&
      s.equalsIgnoreCase(AppConstants.BooleanYesString)

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

  def getLinesWithLineNumbers(lineJoiner : String = " . ") : Array[String] = {
    var lineNumber = 0
    getLines.map(line => {
      lineNumber += 1
      s"$lineNumber$lineJoiner$line"
    })
  }
}
