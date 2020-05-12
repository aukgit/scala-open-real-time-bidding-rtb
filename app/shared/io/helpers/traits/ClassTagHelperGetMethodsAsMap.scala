package shared.io.helpers.traits

import java.lang.reflect.Method

import shared.io.helpers.{ ClassTagHelperConcreteImplementation, EmptyValidateHelper, MapHelper }

import scala.collection.mutable.ArrayBuffer
import scala.reflect.ClassTag

trait ClassTagHelperGetMethodsAsMap {
  this : ClassTagHelperConcreteImplementation =>

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
        value = f,
        defaultCapacity = 1)
    })

    map.toMap
  }
}
