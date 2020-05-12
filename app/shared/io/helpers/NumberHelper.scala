package shared.io.helpers

import shared.com.ortb.model.results.ResultWithSuccessModel

import scala.util.matching.Regex

/**
 * Reference: https://bit.ly/2VY8I53
 */
object NumberHelper {
  def isAllDigits(x : String) : Boolean = x forall Character.isDigit

  def isShort(aString : String) : ResultWithSuccessModel[Short] =
    TryHelper.TryResult[Short](aString.toShort)

  def isInt(aString : String) : ResultWithSuccessModel[Int] =
    TryHelper.TryResult[Int](aString.toInt)

  def isLong(aString : String) : ResultWithSuccessModel[Long] =
    TryHelper.TryResult[Long](aString.toLong)

  def isDouble(aString : String) : ResultWithSuccessModel[Double] =
    TryHelper.TryResult[Double](aString.toDouble)

  def isFloat(aString : String) : ResultWithSuccessModel[Float] =
    TryHelper.TryResult[Float](aString.toFloat)

  def getAs[T](d : Option[T], default : T) : T = {
    if (d.isEmpty) {
      return default
    }

    d.get
  }

  def getAsInt(d : Option[Int]) : Int = {
    if (d.isEmpty) {
      return 0
    }

    d.get
  }

  def getAsDouble(d : Option[Double]) : Double = {
    if (d.isEmpty) {
      return 0
    }

    d.get
  }

  lazy val numberTestingRegex : Regex = "^\\d+$".r

  def isStringNumber(x : String) : Boolean = x match {
    case numberTestingRegex() => true
    case _ => false
  }

  def isNumberType(anyType : Any) : Boolean = {
    anyType.isInstanceOf[Int] ||
      anyType.isInstanceOf[Double] ||
      anyType.isInstanceOf[Byte] ||
      anyType.isInstanceOf[Short] ||
      anyType.isInstanceOf[Float] ||
      anyType.isInstanceOf[Long] ||
      anyType.isInstanceOf[BigDecimal] ||
      anyType.isInstanceOf[Integer] ||
      anyType.isInstanceOf[Integer]
  }
}
