package shared.io.extensions

import shared.io.extensions.traits.primitiveTypes._

/**
 * Reference: https://github.com/Powerspace/scala-openrtb
 */
object TypeConvertExtensions {

  /**
   * Convert a boolean to the related value
   */
  implicit class BooleanConverter(val b : Boolean) extends TypeConvertBoolean

  /**
   * Convert an integer to the related value
   */
  implicit class IntConverter(val i : Int) extends TypeConvertInteger

  /**
   * Convert an string to the related values
   */
  implicit class StringConverter(val s : String) extends TypeConvertString

  implicit class StringOptionConverter(val s : Option[String]) extends TypeConvertOptionString

  implicit class GenericConverter[T](val anyItem : T) extends TypeConvertGeneric[T]

  implicit class GenericIterableConverter[T](val anyItems : Iterable[T]) extends TypeConvertGenericIterable[T]

  implicit class GenericJsonConverter[T](val anyItem : T) extends TypeConvertGenericJson[T]
}
