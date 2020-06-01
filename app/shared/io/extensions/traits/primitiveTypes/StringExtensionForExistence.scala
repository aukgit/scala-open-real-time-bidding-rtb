package shared.io.extensions.traits.primitiveTypes

import shared.io.helpers.EmptyValidateHelper
import shared.io.extensions.TypeConvertExtensions._

trait StringExtensionForExistence extends StringExtensionEssentials {
  lazy val hasCharacter : Boolean = EmptyValidateHelper.isStringDefined(s)
  lazy val isStringEmpty : Boolean = !hasCharacter
  lazy val safeString : String = s.getOrElseDefault()
}
