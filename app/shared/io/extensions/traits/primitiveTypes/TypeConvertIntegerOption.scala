package shared.io.extensions.traits.primitiveTypes

import shared.io.extensions.TypeConvertExtensions._
import shared.io.helpers.EmptyValidateHelper

trait TypeConvertIntegerOption {

  lazy val isEmpty : Boolean = EmptyValidateHelper.isEmpty(i)
  lazy val hasNumber : Boolean = !isEmpty
  lazy val toStringOption : Option[String] = if (hasNumber) self.toString.toSome else None
  lazy protected val self : Int = i.get
  protected val i : Option[Int]

  def toBoolString : String = toBoolean.toString

  def toBoolean : Boolean = if (i.isDefined && self <= 0) false else true
}
