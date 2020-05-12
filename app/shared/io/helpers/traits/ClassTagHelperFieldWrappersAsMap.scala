package shared.io.helpers.traits

import shared.com.ortb.model.reflection
import shared.com.ortb.model.reflection.FieldWrapperModel
import shared.com.ortb.model.results.ResultWithCountSuccessModel
import shared.io.helpers.{ ClassTagHelperConcreteImplementation, EmptyValidateHelper, MapHelper }

import scala.collection.mutable.ArrayBuffer
import scala.reflect.ClassTag

trait ClassTagHelperFieldWrappersAsMap {
  this : ClassTagHelperConcreteImplementation =>

  def getFieldWrapperModelsAsMap[T](implicit ct : ClassTag[T]) :
  ResultWithCountSuccessModel[Map[String, ArrayBuffer[FieldWrapperModel]]] =
    getFieldWrapperModelsAsMap(getClass[T](ct))

  def getFieldWrapperModelsAsMap(classRefection : Class[_]) :
  ResultWithCountSuccessModel[Map[String, ArrayBuffer[FieldWrapperModel]]] = {
    val fields = classRefection.getFields
    val typeName = classRefection.getTypeName
    val length = fields.length
    val classHeader = s"[${ typeName.toString }] "
    if (EmptyValidateHelper.isItemsEmptyDirect(fields)) {
      return ResultWithCountSuccessModel[Map[String, ArrayBuffer[FieldWrapperModel]]](
        Some(Map.empty[String, ArrayBuffer[FieldWrapperModel]]),
        0,
        isSuccess = false,
        s"${ classHeader }Not fields found"
      )
    }

    val map = new collection.mutable.HashMap[String, ArrayBuffer[FieldWrapperModel]](
      length + 1,
      1.2)

    fields.foreach(f => {
      val fieldWrapperModel = reflection.FieldWrapperModel(f)
      MapHelper.hashMapWithArrayBufferAdder.addToArrayBuffer(
        hashMap = map,
        key = fieldWrapperModel.name,
        value = fieldWrapperModel,
        defaultCapacity = 1)
    })

    val finalMap = Some(map.toMap)
    ResultWithCountSuccessModel[Map[String, ArrayBuffer[FieldWrapperModel]]](
      finalMap,
      count = length,
      isSuccess = true,
      s"${ classHeader }Fields found [${ length }]"
    )
  }
}
