package shared.io.helpers.traits.reflection

import shared.com.ortb.model.reflection
import shared.com.ortb.model.reflection.MethodWrapperModel
import shared.com.ortb.model.results.ResultWithCountSuccessModel
import shared.io.helpers.implementation.reflection.ClassTagHelperConcreteImplementation
import shared.io.helpers.{ EmptyValidateHelper, MapHelper }

import scala.collection.mutable.ArrayBuffer
import scala.reflect.ClassTag

trait ClassTagHelperGetMethodWrappersAsMap {
  this : ClassTagHelperConcreteImplementation =>

  def getMethodWrapperModelsAsMap[T](implicit ct : ClassTag[T]) :
  ResultWithCountSuccessModel[Map[String, ArrayBuffer[MethodWrapperModel]]] =
    getMethodWrapperModelsAsMap(getClass[T])

  def getMethodWrapperModelsAsMap(classRefection : Class[_]) :
  ResultWithCountSuccessModel[Map[String, ArrayBuffer[MethodWrapperModel]]] = {
    val methods = classRefection.getMethods
    val typeName = classRefection.getTypeName
    val length = methods.length
    val classHeader = s"[${ typeName.toString }] "
    if (EmptyValidateHelper.isItemsEmptyDirect(methods)) {
      return ResultWithCountSuccessModel[Map[String, ArrayBuffer[MethodWrapperModel]]](
        Some(Map.empty[String, ArrayBuffer[MethodWrapperModel]]),
        0,
        isSuccess = false,
        s"${ classHeader }No methods found"
      )
    }

    val map = new collection.mutable.HashMap[String, ArrayBuffer[MethodWrapperModel]](
      length + 1,
      1.2)

    methods.foreach(w => {
      val methodWrapper = reflection.MethodWrapperModel(w)
      MapHelper.hashMapWithArrayBufferAdder.addToArrayBuffer(
        hashMap = map,
        key = methodWrapper.name,
        value = methodWrapper,
        defaultCapacity = 1)
    })

    val finalMap = Some(map.toMap)
    ResultWithCountSuccessModel[Map[String, ArrayBuffer[MethodWrapperModel]]](
      finalMap,
      count = length,
      isSuccess = true,
      s"${ classHeader }Methods found [${ length }]"
    )
  }
}
