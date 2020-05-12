package shared.io.helpers.traits.reflection

import java.lang.reflect.Method

import shared.com.ortb.enumeration.ReflectionModifier
import shared.io.helpers.implementation.reflection.ClassTagHelperConcreteImplementation

import scala.reflect.ClassTag

trait ClassTagHelperMethods
  extends ClassTagHelperGetMethodsAsMap
    with ClassTagHelperGetMethodWrappersAsMap {
  this : ClassTagHelperConcreteImplementation =>

  def getMethodsWithModifier[T](reflectionModifier : ReflectionModifier)(implicit ct : ClassTag[T]) : Array[Method] =
    getMethods[T].filter(method => reflectionModifier.value == method.getModifiers)

  def getMethods[T](implicit ct : ClassTag[T]) : Array[Method] =
    getClass[T].getMethods
}
