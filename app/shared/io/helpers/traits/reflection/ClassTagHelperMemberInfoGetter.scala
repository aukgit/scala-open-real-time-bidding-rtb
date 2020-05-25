package shared.io.helpers.traits.reflection

import shared.com.ortb.model.reflection.ClassMembersInfoModel
import shared.com.ortb.model.reflection.implementations.ClassMembersInfoBaseConcreteImplementationModel
import shared.io.helpers.implementations.reflection.ClassTagHelperConcreteImplementation
import shared.io.helpers.{ EmptyValidateHelper, ExtractHelper }

import scala.concurrent.Future
import scala.reflect.ClassTag

trait ClassTagHelperMemberInfoGetter {
  this : ClassTagHelperConcreteImplementation =>
  def getMembersInfoForT[T](
    maybeInstance : Option[T]) : ClassMembersInfoModel[T] = {
    if(EmptyValidateHelper.isEmpty(maybeInstance)){
      return null
    }

    getMembersInfoFor[T](maybeInstance.get.getClass, maybeInstance)
  }

  def getMembersInfoFor[T](
    classT : Class[_],
    maybeInstance : Option[T]) : ClassMembersInfoModel[T] = {
    val extractedFields = Future {
      val fields = getFieldWrapperModelsAsMap(classT)
      ExtractHelper.getFromResult(fields)
    }(createDefaultContext())

    val extractedMethods = Future {
      val methods = getMethodWrapperModelsAsMap(classT)
      ExtractHelper.getFromResult(methods)
    }(createDefaultContext())

    val extractedConstructors = Future {
      val constructors = getConstructorWrapperModelsAsMap(classT)
      ExtractHelper.getFromResult(constructors)
    }(createDefaultContext())

    val clx = ClassMembersInfoBaseConcreteImplementationModel(
      classT,
      maybeInstance,
      extractedFields,
      extractedMethods,
      extractedConstructors
    )

    val eventualMembersMap = getEventualMembers[T](clx)

    ClassMembersInfoModel[T](
      classT,
      maybeInstance,
      extractedFields,
      extractedMethods,
      extractedConstructors,
      eventualMembersMap
    )
  }

  def getMembersInfo[T](implicit ct : ClassTag[T]) : ClassMembersInfoModel[T] = {
    val clxReflection = getClass[T](ct)
    getMembersInfoFor[T](clxReflection, None)
  }

  def getMembersInfo[T](clazz : T)(implicit ct : ClassTag[T]) : ClassMembersInfoModel[T] = {
    if (EmptyValidateHelper.isEmptyDirect(clazz)) {
      val clxReflection = getClass[T](ct)
      return getMembersInfoFor(clxReflection, None)
    }

    getMembersInfoFor(clazz.getClass, None)
  }
}
