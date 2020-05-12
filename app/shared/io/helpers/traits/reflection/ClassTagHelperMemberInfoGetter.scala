package shared.io.helpers.traits.reflection

import shared.com.ortb.model.reflection.{ ClassMembersInfoBaseImplementationModel, ClassMembersInfoModel }
import shared.io.helpers.implementation.reflection.ClassTagHelperConcreteImplementation
import shared.io.helpers.traits.ExtractHelperBase

import scala.reflect.ClassTag

trait ClassTagHelperMemberInfoGetter {
  this : ClassTagHelperConcreteImplementation =>
  def getMembersInfo[T](clazz : Class[T]) : ClassMembersInfoModel = {
    val classT = clazz.getClass
    val fields = getFieldWrapperModelsAsMap(classT)
    val methods = getMethodWrapperModelsAsMap(classT)
    val constructors = getConstructorWrapperModelsAsMap[T](ct)
    val extractedFields = ExtractHelperBase.getFromResult(fields)
    val extractedMethods = ExtractHelperBase.getFromResult(methods)
    val extractedConstructors = ExtractHelperBase.getFromResult(constructors)

    val clx = ClassMembersInfoBaseImplementationModel(
      classT,
      extractedFields,
      extractedMethods,
      extractedConstructors
    )
    val eventualMembersMap = getEventualMembers(clx)

    ClassMembersInfoModel(
      classT,
      extractedFields,
      extractedMethods,
      extractedConstructors,
      eventualMembersMap
    )
  }

  def getMembersInfo[T](implicit ct : ClassTag[T]) : ClassMembersInfoModel = {
    ???
  }
}
