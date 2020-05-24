package shared.io.helpers.traits.reflection

import java.lang.reflect.Method

import shared.com.ortb.model.reflection
import shared.com.ortb.model.reflection.MethodWrapperModel
import shared.com.ortb.model.results.ResultWithCountSuccessModel
import shared.io.helpers.implementations.reflection.ClassTagHelperConcreteImplementation
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
    val publicMethods = classRefection.getMethods
    val staticMethods = classRefection.getDeclaredMethods
    val typeName = classRefection.getTypeName
    val classHeader = s"[${ typeName.toString }] "
    val isEmpty = EmptyValidateHelper.isItemsEmptyDirect(publicMethods) &&
      EmptyValidateHelper.isItemsEmptyDirect(staticMethods)

    if (isEmpty) {
      return ResultWithCountSuccessModel[Map[String, ArrayBuffer[MethodWrapperModel]]](
        Some(Map.empty[String, ArrayBuffer[MethodWrapperModel]]),
        0,
        isSuccess = false,
        s"${ classHeader }No methods found"
      )
    }

    val length = publicMethods.length + staticMethods.length
    val map = new collection.mutable.HashMap[String, ArrayBuffer[MethodWrapperModel]](
      length + 1,
      1.2)

    def addMethod(method : Method) : Unit = {
      val methodWrapper = reflection.MethodWrapperModel(method)
      MapHelper.hashMapWithArrayBufferAdder.addToArrayBuffer(
        hashMap = map,
        key = methodWrapper.name,
        value = methodWrapper,
        defaultCapacity = 1)
    }

    publicMethods.foreach(addMethod)
    staticMethods.foreach(addMethod)

    val finalMap = Some(map.toMap)
    ResultWithCountSuccessModel[Map[String, ArrayBuffer[MethodWrapperModel]]](
      finalMap,
      count = length,
      isSuccess = true,
      s"${ classHeader }Methods found [${ length }]"
    )
  }
}
