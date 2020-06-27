package shared.io.extensions.traits.primitiveTypes

import shared.io.helpers.EmptyValidateHelper


trait StringExtensionForExistence extends StringExtensionEssentials {
  lazy val hasCharacter : Boolean = EmptyValidateHelper.isStringDefined(s)
  lazy val isStringEmpty : Boolean = !hasCharacter
}
