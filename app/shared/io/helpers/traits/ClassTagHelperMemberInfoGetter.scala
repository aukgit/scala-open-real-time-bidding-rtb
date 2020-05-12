package shared.io.helpers.traits

import shared.com.ortb.model.reflection.{ ClassMembersInfoBaseImplementationModel, ClassMembersInfoModel }
import shared.io.helpers.ClassTagHelperConcreteImplementation

import scala.reflect.ClassTag

trait ClassTagHelperMemberInfoGetter {
  this : ClassTagHelperConcreteImplementation =>
  def getMembersInfo[T](clazz : Class[T]) : ClassMembersInfoModel = {
    val classT = clazz.getClass
    val fields = getFieldWrapperModelsAsMap(classT)
    val methods = getMethodWrapperModelsAsMap(classT)
    val constructors = getConstructorWrapperModelsAsMap[T](ct)
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
