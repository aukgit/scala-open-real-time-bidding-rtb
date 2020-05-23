package shared.io.extensions.traits.primitiveTypes

import shared.io.helpers.EmptyValidateHelper

trait TypeConvertOptionString {
  lazy val hasCharacter : Boolean = EmptyValidateHelper.isOptionStringDefined(s)
  lazy val isEmpty : Boolean = !hasCharacter
  lazy val getDefinedOrEmptyString : String = if (hasCharacter) s.get else ""
  protected val s : Option[String]
}
