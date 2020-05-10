package shared.io.helpers.traits

import java.lang.reflect.{ Field, Member, Method, Parameter }

import shared.com.ortb.enumeration.ReflectionModifier
import shared.com.ortb.model.results.{ ResultModel, ResultWithCountSuccessModel, ResultWithSuccessModel }
import shared.com.ortb.model.{ reflection, _ }
import shared.com.ortb.model.reflection.{ ClassMembersInfoModel, ConstructorWrapperModel, FieldWrapperModel, MethodWrapperModel }
import shared.io.helpers.{ EmptyValidateHelper, _ }

import scala.collection.mutable.ArrayBuffer
import scala.reflect.ClassTag

trait ExtractHelper {
  def get[T](item : Option[T]) : T = {
    if(EmptyValidateHelper.isEmpty(item)){
      return null.asInstanceOf[T]
    }

    item.get
  }

  def getFromResult[T](item : Option[ResultModel[T]]) : T = {
    if(EmptyValidateHelper.isEmpty(item) ||
      EmptyValidateHelper.isEmpty(item.get.result)){
      return null.asInstanceOf[T]
    }

    item.get.result.get
  }

  def getFromCountResult[T](item : Option[ResultWithCountSuccessModel[T]]) : T = {
    getFromResult(item)
  }
}

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
        value = f,
        defaultCapacity = 1)
    })

    map.toMap
  }

  def getMethodWrapperModelsAsMap[T](implicit ct : ClassTag[T]) :
  ResultWithCountSuccessModel[Map[String, ArrayBuffer[MethodWrapperModel]]] = {
    val methods = getMethods[T]
    val typeName = ct.runtimeClass.getTypeName
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

  def getFieldWrapperModelsAsMap[T](implicit ct : ClassTag[T]) :
  ResultWithCountSuccessModel[Map[String, ArrayBuffer[FieldWrapperModel]]] = {
    val fields = getFields[T]
    val typeName = ct.runtimeClass.getTypeName
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

  def getConstructorWrapperModelsAsMap[T](implicit ct : ClassTag[T]) :
  ResultWithCountSuccessModel[Map[String, ArrayBuffer[ConstructorWrapperModel]]] = {
    val constructors = getConstructors[T]
    val typeName = ct.runtimeClass.getTypeName
    val length = constructors.length
    val classHeader = s"[${ typeName.toString }] "
    if (EmptyValidateHelper.isItemsEmptyDirect(constructors)) {
      return ResultWithCountSuccessModel[Map[String, ArrayBuffer[ConstructorWrapperModel]]](
        Some(Map.empty[String, ArrayBuffer[ConstructorWrapperModel]]),
        0,
        isSuccess = false,
        s"${ classHeader }No constructors found"
      )
    }

    val map = new collection.mutable.HashMap[String, ArrayBuffer[ConstructorWrapperModel]](
      length + 1,
      1.2)

    constructors.foreach(constructor => {
      val fieldWrapperModel = reflection.ConstructorWrapperModel(constructor)
      MapHelper.hashMapWithArrayBufferAdder.addToArrayBuffer(
        hashMap = map,
        key = fieldWrapperModel.name,
        value = fieldWrapperModel,
        defaultCapacity = 1)
    })

    val finalMap = Some(map.toMap)
    ResultWithCountSuccessModel[Map[String, ArrayBuffer[ConstructorWrapperModel]]](
      finalMap,
      count = length,
      isSuccess = true,
      s"${ classHeader }Constructors found [${ length }]"
    )
  }

  def getMembersInfo[T](implicit ct : ClassTag[T]) : ClassMembersInfoModel = {
    val fields = getFieldWrapperModelsAsMap[T](ct)
    val methods = getMethodWrapperModelsAsMap[T](ct)
    val constructors = getMethodWrapperModelsAsMap[T](ct)
//
//    ClassMembersInfoModel(
//      getClass[T],
//      fields.result.get
//    )
    ???
  }

  def getSuperClassFields[T](implicit ct : ClassTag[T]) : Array[Field] =
    getClass[T].getSuperclass.getFields

  def getFieldsWithModifier[T](reflectionModifier : ReflectionModifier)(implicit ct : ClassTag[T]) : Array[Field] =
    getFields[T].filter(field => reflectionModifier.value == field.getModifiers)

  def getMethods[T](implicit ct : ClassTag[T]) : Array[Method] =
    getClass[T].getMethods

  def getMethodsWithModifier[T](reflectionModifier : ReflectionModifier)(implicit ct : ClassTag[T]) : Array[Method] =
    getMethods[T].filter(method => reflectionModifier.value == method.getModifiers)

  def getFilterMembersWithModifier[T](
    members : Iterable[Member],
    reflectionModifier : ReflectionModifier)(implicit ct : ClassTag[T]) : Iterable[Member] =
    members.filter(member => reflectionModifier.value == member.getModifiers)

  def getConstructors[T](implicit ct : ClassTag[T]) : Array[java.lang.reflect.Constructor[_]] =
    getClass[T].getDeclaredConstructors
}
