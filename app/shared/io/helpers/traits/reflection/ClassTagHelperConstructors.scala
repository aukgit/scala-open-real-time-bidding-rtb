package shared.io.helpers.traits.reflection

import shared.com.ortb.model.reflection
import shared.com.ortb.model.reflection.ConstructorWrapperModel
import shared.com.ortb.model.results.ResultWithCountSuccessModel
import shared.io.helpers.implementations.reflection.ClassTagHelperConcreteImplementation
import shared.io.helpers.{ EmptyValidateHelper, MapHelper }

import scala.collection.mutable.ArrayBuffer
import scala.reflect.ClassTag

trait ClassTagHelperConstructors extends ClassTagHelperConstructorWrappersAsMap {
  this : ClassTagHelperConcreteImplementation =>

  def getConstructors[T](implicit ct : ClassTag[T]) : Array[java.lang.reflect.Constructor[_]] =
    getClass[T].getDeclaredConstructors
}
