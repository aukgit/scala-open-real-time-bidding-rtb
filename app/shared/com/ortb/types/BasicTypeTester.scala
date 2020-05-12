package shared.com.ortb.types

import shared.io.helpers.{ CastingHelper, NumberHelper }

class BasicTypeTester(val value : Any) {
  lazy val isEmpty : Boolean = value == null || value == None || value == Nil
  lazy val isOption : Boolean = !isEmpty && value.isInstanceOf[Option[_]]
  lazy val isIterable : Boolean = !isEmpty && value.isInstanceOf[Iterable[_]]
  lazy val isList : Boolean = !isEmpty && value.isInstanceOf[List[_]]
  lazy val isArray : Boolean = !isEmpty && value.isInstanceOf[Array[_]]
  lazy val isVector : Boolean = !isEmpty && value.isInstanceOf[Vector[_]]
  lazy val isMap : Boolean = !isEmpty && value.isInstanceOf[Map[_, _]]

  lazy val isNumber : Boolean = !isEmpty && NumberHelper.isNumberType(value)
  lazy val toOption : Option[_] = value.asInstanceOf[Option[_]]
  lazy val isInt : Boolean = !isEmpty && value.isInstanceOf[Int]
  lazy val isInteger : Boolean = !isEmpty && value.isInstanceOf[Integer]

  lazy val isIntOrInteger : Boolean = isInt || isInteger
  lazy val isLong : Boolean = !isEmpty && value.isInstanceOf[Long]
  lazy val isIntOrIntegerOrLong : Boolean = isIntOrInteger || isLong
  lazy val isDouble : Boolean = !isEmpty && value.isInstanceOf[Double]
  lazy val isFloat : Boolean = !isEmpty && value.isInstanceOf[Float]
  lazy val typeClass : Class[_] = if (!isEmpty) value.getClass else null
  lazy val isOptionNumber : Boolean =
    isOption &&
      NumberHelper.isNumberType(toOption.get)

  def as[T] : T = value.asInstanceOf[T]

  def is[T] : Boolean = value.isInstanceOf[T]

  def getSafeAs[T] : Option[T] = CastingHelper.safeCastAs[T](value)

  def getOptionAs[T] : Option[T] = value.asInstanceOf[Option[T]]

  def typeClassName(unknownTypeName : String = "Any") : String =
    if (typeClass != null)
      typeClass.getTypeName
    else
      unknownTypeName
}
