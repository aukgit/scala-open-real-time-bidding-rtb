package shared.io.helpers.traits

import shared.com.ortb.model.results.ResultWithSuccessModel
import shared.io.helpers.TryHelper

import scala.util.matching.Regex

trait NumberHelperBase {
  lazy val numberTestingRegex : Regex = "^\\d+$".r

  def isAllDigits(x : String) : Boolean = x forall Character.isDigit

  def getShortResult(aString : String) : ResultWithSuccessModel[Short] =
    TryHelper.TryResult[Short](aString.toShort)

  def getIntResult(aString : String) : ResultWithSuccessModel[Int] =
    TryHelper.TryResult[Int](aString.toInt)

  def getLongResult(aString : String) : ResultWithSuccessModel[Long] =
    TryHelper.TryResult[Long](aString.toLong)

  def getDoubleResult(aString : String) : ResultWithSuccessModel[Double] =
    TryHelper.TryResult[Double](aString.toDouble)

  def getFloatResult(aString : String) : ResultWithSuccessModel[Float] =
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
