package shared.io.helpers.traits.reflection

import java.lang.reflect.Field

import shared.com.ortb.model.reflection
import shared.com.ortb.model.reflection.FieldWrapperModel
import shared.com.ortb.model.results.ResultWithCountSuccessModel
import shared.io.helpers.implementations.reflection.ClassTagHelperConcreteImplementation
import shared.io.helpers.{ EmptyValidateHelper, MapHelper }

import scala.collection.mutable.ArrayBuffer
import scala.reflect.ClassTag

trait ClassTagHelperFieldWrappersAsMap {
  this : ClassTagHelperConcreteImplementation =>

  def getFieldWrapperModelsAsMap[T](implicit ct : ClassTag[T]) :
  ResultWithCountSuccessModel[Map[String, ArrayBuffer[FieldWrapperModel]]] =
    getFieldWrapperModelsAsMap(getClass[T](ct))

  def getFieldWrapperModelsAsMap(classRefection : Class[_]) :
  ResultWithCountSuccessModel[Map[String, ArrayBuffer[FieldWrapperModel]]] = {
    val privateFields = classRefection.getDeclaredFields
    val publicFields = classRefection.getFields
    val typeName = classRefection.getTypeName
    val classHeader = s"[${ typeName.toString }] "
    val isEmpty = EmptyValidateHelper.isItemsEmptyDirect(publicFields) &&
      EmptyValidateHelper.isItemsEmptyDirect(privateFields)

    if (isEmpty) {
      return ResultWithCountSuccessModel[Map[String, ArrayBuffer[FieldWrapperModel]]](
        Some(Map.empty[String, ArrayBuffer[FieldWrapperModel]]),
        0,
        isSuccess = false,
        s"${ classHeader }Not fields found"
      )
    }

    val length = publicFields.length + privateFields.length
    val map = new collection.mutable.HashMap[String, ArrayBuffer[FieldWrapperModel]](
      length + 1,
      1.2)

    def addField(field : Field) : Unit = {
      val fieldWrapperModel = reflection.FieldWrapperModel(field)
      MapHelper.hashMapWithArrayBufferAdder.addToArrayBuffer(
        hashMap = map,
        key = fieldWrapperModel.name,
        value = fieldWrapperModel,
        defaultCapacity = 1)
    }

    publicFields.foreach(addField)
    privateFields.foreach(addField)

    val finalMap = Some(map.toMap)
    ResultWithCountSuccessModel[Map[String, ArrayBuffer[FieldWrapperModel]]](
      finalMap,
      count = length,
      isSuccess = true,
      s"${ classHeader }Fields found [${ length }]"
    )
  }
}
