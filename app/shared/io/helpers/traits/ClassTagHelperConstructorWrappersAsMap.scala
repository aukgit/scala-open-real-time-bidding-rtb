package shared.io.helpers.traits

import shared.com.ortb.model.reflection
import shared.com.ortb.model.reflection.ConstructorWrapperModel
import shared.com.ortb.model.results.ResultWithCountSuccessModel
import shared.io.helpers.{ ClassTagHelperConcreteImplementation, EmptyValidateHelper, MapHelper }

import scala.collection.mutable.ArrayBuffer
import scala.reflect.ClassTag

trait ClassTagHelperConstructorWrappersAsMap {
  this : ClassTagHelperConcreteImplementation =>
  def getConstructorWrapperModelsAsMap[T](implicit ct : ClassTag[T]) :
  ResultWithCountSuccessModel[Map[Int, ArrayBuffer[ConstructorWrapperModel]]] = {
    val constructors = getConstructors[T]
    val typeName = ct.runtimeClass.getTypeName
    val length = constructors.length
    val classHeader = s"[${ typeName.toString }] "
    if (EmptyValidateHelper.isItemsEmptyDirect(constructors)) {
      return ResultWithCountSuccessModel[Map[Int, ArrayBuffer[ConstructorWrapperModel]]](
        Some(Map.empty[Int, ArrayBuffer[ConstructorWrapperModel]]),
        0,
        isSuccess = false,
        s"${ classHeader }No constructors found"
      )
    }

    val map = new collection.mutable.HashMap[Int, ArrayBuffer[ConstructorWrapperModel]](
      length + 1,
      1.2)

    var index = 0
    constructors.foreach(constructor => {
      val fieldWrapperModel = reflection.ConstructorWrapperModel(constructor)
      MapHelper.hashMapWithArrayBufferAdder.addToArrayBuffer(
        hashMap = map,
        key = index,
        value = fieldWrapperModel,
        defaultCapacity = 1)

      index += 1
    })

    val finalMap = Some(map.toMap)
    ResultWithCountSuccessModel[Map[Int, ArrayBuffer[ConstructorWrapperModel]]](
      finalMap,
      count = length,
      isSuccess = true,
      s"${ classHeader }Constructors found [${ length }]"
    )
  }
}
