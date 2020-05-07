package shared.io.helpers.traits

import java.lang.reflect.{ Field, Method }

import scala.reflect.ClassTag

trait ClassTagHelperBase {
  def getClass[T](implicit ct : ClassTag[T]) : Class[_] =
    ct.runtimeClass

  def getFields[T](implicit ct : ClassTag[T]) : Iterable[Field] =
    getClass[T].getDeclaredFields

  def getMethods[T](implicit ct : ClassTag[T]) : Array[Method] =
    getClass[T].getMethods

  def getConstructors[T](implicit ct : ClassTag[T]) : Array[java.lang.reflect.Constructor[_]] =
    getClass[T].getDeclaredConstructors
}
