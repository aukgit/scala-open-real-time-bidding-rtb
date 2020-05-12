package shared.com.ortb.types

import shared.io.helpers.{ CastingHelper, NumberHelper }

class BasicTypeTester(val value : Any) {
  lazy val isEmpty : Boolean = value == null || value == None
  lazy val isOption : Boolean = is[Option[_]]
  lazy val isIterable : Boolean = is[Iterable[_]]
  lazy val isList : Boolean = is[List[_]]
  lazy val isArray : Boolean = is[Array[_]]
  lazy val isVector : Boolean = is[Vector[_]]
  lazy val isMap : Boolean = is[Map[_, _]]

  lazy val isNumber : Boolean = !isEmpty && NumberHelper.isNumberType(value)
  lazy val toOption : Option[_] = value.asInstanceOf[Option[_]]
  lazy val isInt : Boolean = is[Int]
  lazy val isInteger : Boolean = is[Integer]

  lazy val isIntOrInteger : Boolean = isInt || isInteger
  lazy val isLong : Boolean = is[Long]
  lazy val isIntOrIntegerOrLong : Boolean = isIntOrInteger || isLong
  lazy val isDouble : Boolean = is[Double]
  lazy val isFloat : Boolean = is[Float]
  lazy val typeClass : Class[_] = if (!isEmpty) value.getClass else null
  lazy val isOptionNumber : Boolean =
    isOption &&
      NumberHelper.isNumberType(toOption.get)

  def getAs[T] : T = value.asInstanceOf[T]

  def getSafeAs[T] : Option[T] = CastingHelper.safeCastAs[T](value)

  def getOptionAs[T] : Option[T] = value.asInstanceOf[Option[T]]

  def is[T] : Boolean = !isEmpty && value.isInstanceOf[T]

  def typeClassName(unknownTypeName : String = "Any") : String =
    if (typeClass != null)
      typeClass.getTypeName
    else
      unknownTypeName
}
