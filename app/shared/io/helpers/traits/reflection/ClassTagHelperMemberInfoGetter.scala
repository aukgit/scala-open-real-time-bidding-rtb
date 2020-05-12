package shared.io.helpers.traits.reflection

import shared.com.ortb.model.reflection.{ ClassMembersInfoBaseImplementationModel, ClassMembersInfoModel }
import shared.io.helpers.ExtractHelper
import shared.io.helpers.implementation.reflection.ClassTagHelperConcreteImplementation
import shared.io.helpers.traits.ExtractHelperBase

import scala.reflect.ClassTag

trait ClassTagHelperMemberInfoGetter {
  this : ClassTagHelperConcreteImplementation =>
  def getMembersInfo[T](clazz : Class[T]) : ClassMembersInfoModel = {
    val classT = clazz.getClass
    val fields = getFieldWrapperModelsAsMap(classT)
    val methods = getMethodWrapperModelsAsMap(classT)
    val constructors = getConstructorWrapperModelsAsMap(classT)
    val extractedFields = ExtractHelper.getFromResult(fields)
    val extractedMethods = ExtractHelper.getFromResult(methods)
    val extractedConstructors = ExtractHelper.getFromResult(constructors)

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
