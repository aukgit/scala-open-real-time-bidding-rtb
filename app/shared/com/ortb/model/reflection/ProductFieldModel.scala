package shared.com.ortb.model.reflection

import java.lang.reflect.Field

import shared.io.helpers.ReflectionHelper

case class ProductFieldModel(
  name : String,
  value : Any,
  field : Field,
  index : Int) {
  lazy val fieldType : Class[_] = field.getType

  def toStringField : String = {
    val typeClassName = ReflectionHelper.getTypeName(fieldType)

    s"${ name } : ${ typeClassName }"
  }
}
