package shared.io.extensions.traits.primitiveTypes

import shared.io.helpers.EmptyValidateHelper

trait PrimitiveTypeStringConversion {
  val s : String

  lazy private val isDefined = EmptyValidateHelper.isStringDefined(s)
  lazy private val isTrueString = isDefined && s.equalsIgnoreCase("true")
  lazy private val isYesString = isDefined && s.equalsIgnoreCase("yes")

  lazy private val isTrue =
    isTrueString ||
      s == "1" ||
      isYesString

  def toBoolString : String = toBoolean.toString

  def toBoolean : Boolean = if (isTrue) true else false
}
