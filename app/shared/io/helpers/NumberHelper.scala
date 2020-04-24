package shared.io.helpers

import shared.com.ortb.model.results.ResultWithBooleanModel

/**
 * Reference: https://bit.ly/2VY8I53
 */
object NumberHelper {
  def isAllDigits(x : String) : Boolean = x forall Character.isDigit

  def isShort(aString : String) : ResultWithBooleanModel[Short] =
    TryHelper.TryResult[Short](aString.toShort)

  def isInt(aString : String) : ResultWithBooleanModel[Int] =
    TryHelper.TryResult[Int](aString.toInt)

  def isLong(aString : String) : ResultWithBooleanModel[Long] =
    TryHelper.TryResult[Long](aString.toLong)

  def isDouble(aString : String) : ResultWithBooleanModel[Double] =
    TryHelper.TryResult[Double](aString.toDouble)

  def isFloat(aString : String) : ResultWithBooleanModel[Float] =
    TryHelper.TryResult[Float](aString.toFloat)
}
