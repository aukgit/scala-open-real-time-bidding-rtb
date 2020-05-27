package shared.io.extensions.traits.genericTypes

import shared.io.helpers.EmptyValidateHelper

trait TypeConvertGeneric[T] {
  lazy val isEmpty : Boolean = EmptyValidateHelper.isEmptyAny(anyItem)
  lazy val isDefined : Boolean = !isEmpty
  lazy val toSome : Option[T] = Some(anyItem)
  lazy val toOption : Option[T] = Some(anyItem)
  lazy val toMaybe : Option[T] = Some(anyItem)
  protected val anyItem : T
}
