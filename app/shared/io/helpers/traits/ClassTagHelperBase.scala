package shared.io.helpers.traits

import java.lang.reflect.{ Field, Member, Method }
import java.util.concurrent.ConcurrentHashMap

import shared.com.ortb.enumeration.ReflectionModifier
import shared.com.ortb.manager.traits.{ CreateDefaultContext, DefaultExecutionContextManagerConcreteImplementation }
import shared.com.ortb.model.reflection
import shared.com.ortb.model.reflection.{ MemberWrapperBaseModel, _ }
import shared.com.ortb.model.results.ResultWithCountSuccessModel
import shared.io.helpers.{ EmptyValidateHelper, _ }

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.{ ExecutionContext, Future }
import scala.reflect.ClassTag

trait ClassTagHelperBase extends CreateDefaultContext {
  lazy val executionContextManager = new DefaultExecutionContextManagerConcreteImplementation

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

  def getMembersInfo[T](implicit ct : ClassTag[T]) : ClassMembersInfoModel = {
    val fields = getFieldWrapperModelsAsMap[T](ct)
    val methods = getMethodWrapperModelsAsMap[T](ct)
    val constructors = getConstructorWrapperModelsAsMap[T](ct)
    val extractedFields = ExtractHelper.getFromResult(fields)
    val extractedMethods = ExtractHelper.getFromResult(methods)
    val extractedConstructors = ExtractHelper.getFromResult(constructors)
    val classT = getClass[T]

    val clx = ClassMembersInfoBaseImplementationModel(
      classT,
      extractedFields,
      extractedMethods,
      extractedConstructors
    )

    val eventualMembersMap = getEventualMembersMap(clx)

    ClassMembersInfoModel(
      classT,
      extractedFields,
      extractedMethods,
      extractedConstructors,
      eventualMembersMap
    )
    ???
  }

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

  lazy val defaultMemberPossibility : Int = 30

  def getEventualMembers(
    classMembersInfo : ClassMembersInfoBaseImplementationModel) :
  Future[ResultWithCountSuccessModel[scala.Array[MemberWrapperBaseModel]]] = {
    Future {
      val arrayBuffer = new ArrayBuffer[MemberWrapperBaseModel](defaultMemberPossibility)
      val abW = new ConcurrentArrayBufferWrapper(arrayBuffer)
      var totalCount = 0

      def processMember(member : Member) : Unit = {
        abW.addOne(new MemberWrapperConcreteModel(member))
        totalCount += 1
      }

      ParallelTaskHelper.runInThreads(
        "members collector",
        () =>
          classMembersInfo
            .classType
            .getFields
            .foreach(processMember),
        () => classMembersInfo
          .classType
          .getMethods
          .foreach(processMember),
        () => classMembersInfo
          .classType
          .getConstructors
          .foreach(processMember)
      )

      ResultWithCountSuccessModel(
        result = Some(arrayBuffer.toArray),
        count = totalCount,
        isSuccess = true
      )
    }(createDefaultContext())
  }

  def getEventualMembersMap(
    classMembersInfo : ClassMembersInfoBaseImplementationModel) :
  Future[ResultWithCountSuccessModel[ConcurrentHashMap[String, ArrayBuffer[MemberWrapperBaseModel]]]] = {
    Future {
      val map = new ConcurrentHashMap[String, ArrayBuffer[MemberWrapperBaseModel]]
      var totalCount = 0

      def processMember(memberWrapperModel : MemberWrapperBaseModel) : Unit = {
        MapHelper
          .hashMapWithArrayBufferAdder
          .concurrentMapAddToArrayBuffer(
            map,
            memberWrapperModel.name,
            memberWrapperModel)

        totalCount += 1
      }

      ParallelTaskHelper.runInThreads(
        "members collector",
        () =>
          classMembersInfo
            .fields
            .values
            .flatten
            .foreach(processMember),
        () => classMembersInfo
          .methods
          .values
          .flatten
          .foreach(processMember),
        () => classMembersInfo
          .constructors
          .values
          .flatten
          .foreach(processMember)
      )

      ResultWithCountSuccessModel(
        result = Some(map),
        count = totalCount,
        isSuccess = true
      )
    }(createDefaultContext())
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

  implicit override def createDefaultContext() : ExecutionContext = executionContextManager.defaultExecutionContext
}
