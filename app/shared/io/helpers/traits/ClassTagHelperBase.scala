package shared.io.helpers.traits

import java.lang.reflect.{ Field, Member, Method }

import shared.com.ortb.enumeration.ReflectionModifier
import shared.io.helpers.{ EmptyValidateHelper, _ }

import scala.collection.mutable.ArrayBuffer
import scala.reflect.ClassTag

trait ClassTagHelperBase {
  def getClass[T](implicit ct : ClassTag[T]) : Class[_] =
    ct.runtimeClass

  def getFields[T](implicit ct : ClassTag[T]) : Array[Field] =
    getClass[T].getDeclaredFields

  def getFieldsAsMap[T](implicit ct : ClassTag[T]) : Map[String, Field] = {
    val fields = getFields[T]
    val length = fields.length
    if (EmptyValidateHelper.isItemsEmptyDirect(fields)) {
      return Map.empty
    }

    val map = new collection.mutable.HashMap[String, Field](length + 1, 1.2)
    fields.foreach(f => map.addOne(f.getName -> f))

    map.toMap
  }

  def getMethodsAsMap[T](implicit ct : ClassTag[T]) : Map[String, ArrayBuffer[Method]] = {
    val methods = getMethods[T]
    val length = methods.length
    if (EmptyValidateHelper.isItemsEmptyDirect(methods)) {
      return Map.empty
    }

    val map = new collection.mutable.HashMap[String, ArrayBuffer[Method]](
      length + 1,
      1.2)

    methods.foreach(f => {
      val key = f.getName
      MapHelper.hashMapWithArrayBufferAdder.addToArrayBuffer(
        hashMap = map,
        key = key,
        value = f)
    })

    map.toMap
  }

  def getSuperClassFields[T](implicit ct : ClassTag[T]) : Array[Field] =
    getClass[T].getSuperclass.getFields

  def getFieldsWithModifier[T](reflectionModifier : ReflectionModifier)(implicit ct : ClassTag[T]) : Array[Field] =
    getFields[T].filter(w => reflectionModifier.value == w.getModifiers)

  def getMethods[T](implicit ct : ClassTag[T]) : Array[Method] =
    getClass[T].getMethods

  def getMethodsWithModifier[T](reflectionModifier : ReflectionModifier)(implicit ct : ClassTag[T]) : Array[Method] =
    getMethods[T].filter(w => reflectionModifier.value == w.getModifiers)

  def getFilterMembersWithModifier[T](
    members: Iterable[Member],
    reflectionModifier : ReflectionModifier)(implicit ct : ClassTag[T]) : Iterable[Member] =
    members.filter(w => reflectionModifier.value == w.getModifiers)

  def getConstructors[T](implicit ct : ClassTag[T]) : Array[java.lang.reflect.Constructor[_]] =
    getClass[T].getDeclaredConstructors
}
