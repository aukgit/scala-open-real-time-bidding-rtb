package shared.io.extensions

import shared.com.ortb.constants.AppConstants
import shared.io.extensions.traits.asyncTypes.{ TypeConvertGenericFuture, TypeConvertGenericIterablesFuture }
import shared.io.extensions.traits.genericTypes._
import shared.io.extensions.traits.primitiveTypes._
import shared.io.helpers._

import scala.concurrent.Future

/**
 * Reference: https://github.com/Powerspace/scala-openrtb
 */
object TypeConvertExtensions {

  /**
   * Convert a boolean to the related value
   */
  implicit class BooleanConverter(protected val b : Boolean) extends TypeConvertBoolean

  /**
   * Convert an integer to the related value
   */
  implicit class IntConverter(protected val i : Int) extends TypeConvertInteger

  /**
   * Convert an integer to the related value
   */
  implicit class IntOptionConverter(protected val i : Option[Int]) extends TypeConvertIntegerOption

  /**
   * Convert an string to the related values
   */
  implicit class StringConverter(protected val s : String) extends TypeConvertString

  implicit class StringOptionConverter(protected val s : Option[String]) extends TypeConvertOptionString

  implicit class GenericConverter[T](protected val anyItem : T) extends TypeConvertGeneric[T]

  implicit class GenericArrayConverter[T](protected val array : Array[T]) extends TypeConvertGenericArray[T]

  implicit class GenericListConverter[T](protected val list : List[T]) extends TypeConvertGenericList[T]

  implicit class GenericVectorConverter[T](protected val vector : Vector[T]) extends TypeConvertGenericVector[T]

  implicit class StringIterableConverter(protected val anyItems : Iterable[String]) extends TypeConvertStringIterable

  implicit class StringArrayConverter(protected val array : Array[String]) extends TypeConvertStringArray

  implicit class GenericIterableConverter[T](protected val anyItems : Iterable[T]) extends TypeConvertGenericIterable[T]

  implicit class GenericJsonConverter[T](protected val anyItem : T) extends TypeConvertGenericJson[T]

  implicit class GenericFutureConverter[T](protected val eventualRequest : Future[T]) extends TypeConvertGenericFuture[T]

  implicit class GenericIterableFutureConverter[T](protected val eventualRequests : Iterable[Future[T]])
    extends TypeConvertGenericIterablesFuture[T]

}
