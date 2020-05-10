package shared.com.ortb

import shared.io.helpers.NumberHelper

class BasicTypeTester(val value : Any) {
  lazy val isEmpty : Boolean = value == null || value == None
  lazy val isOption : Boolean = !isEmpty && value.isInstanceOf[Option[_]]
  lazy val isIterable : Boolean = !isEmpty && value.isInstanceOf[Iterable[_]]
  lazy val isList : Boolean = !isEmpty && value.isInstanceOf[List[_]]
  lazy val typeClass : Class[_] = if (!isEmpty) value.getClass else null

  def isTypeOf[T] : Boolean = !isEmpty && value.isInstanceOf[T]

  lazy val isNumber : Boolean = !isEmpty && NumberHelper.isNumberType(value)

  def typeClassName(unknownTypeName : String = "Any") : String =
    if (typeClass != null)
      typeClass.getTypeName
    else
      unknownTypeName
}
