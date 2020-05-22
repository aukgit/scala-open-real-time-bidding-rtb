package shared.io.extensions.traits.primitiveTypes

import shared.io.helpers.EmptyValidateHelper

trait PrimitiveTypeStringConversion {
  lazy private val isTrue = EmptyValidateHelper.isStringDefined(s) ||
    s.equalsIgnoreCase("true") ||
    s == "1" ||
    s.equalsIgnoreCase("yes")
  val s : String

  def toBoolString : String = toBoolean.toString

  def toBoolean : Boolean = if (isTrue) true else false
}
