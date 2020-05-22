package shared.io.extensions

import shared.io.extensions.traits.primitiveTypes._

/**
 * Reference: https://github.com/Powerspace/scala-openrtb
 */
object PrimitiveTypeExtensions {

  /**
   * Convert a boolean to the related value
   */
  implicit class BooleanConverter(val b : Boolean) extends PrimitiveTypeBooleanConversion

  /**
   * Convert an integer to the related value
   */
  implicit class IntConverter(val i : Int) extends PrimitiveTypeIntegerConversion

  /**
   * Convert an string to the related values
   */
  implicit class StringConverter(val s : String) extends PrimitiveTypeStringConversion

}
