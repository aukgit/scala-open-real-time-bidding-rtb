package shared.io.helpers.traits

import java.lang.reflect.Field

import shared.com.ortb.enumeration.ReflectionModifier
import shared.io.helpers.ClassTagHelperConcreteImplementation

import scala.reflect.ClassTag

trait ClassTagHelperFields
  extends ClassTagHelperFieldWrappersAsMap
    with ClassTagHelperFieldsAsMap {
  this : ClassTagHelperConcreteImplementation =>

  def getSuperClassFields[T](implicit ct : ClassTag[T]) : Array[Field] =
    getClass[T].getSuperclass.getFields

  def getFieldsWithModifier[T](reflectionModifier : ReflectionModifier)(implicit ct : ClassTag[T]) : Array[Field] =
    getFields[T].filter(field => reflectionModifier.value == field.getModifiers)

  def getFields[T](implicit ct : ClassTag[T]) : Array[Field] =
    getClass[T].getDeclaredFields
}
