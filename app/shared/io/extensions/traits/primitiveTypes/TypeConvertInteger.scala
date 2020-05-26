package shared.io.extensions.traits.primitiveTypes

import shared.io.helpers.EmptyValidateHelper

trait TypeConvertInteger {
  protected val i : Int

  lazy val toStringOption : Option[String] = Some(i.toString)

  def toBoolString : String = toBoolean.toString

  def toBoolean : Boolean = if (i <= 0) false else true
}


