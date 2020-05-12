package shared.io.helpers.traits.reflection

import shared.com.ortb.model.reflection.{ ClassMembersInfoImplementationModel, ClassMembersInfoModel }
import shared.io.helpers.ExtractHelper
import shared.io.helpers.implementation.reflection.ClassTagHelperConcreteImplementation
import shared.io.helpers.traits.ExtractHelperBase

import scala.concurrent.Future
import scala.reflect.ClassTag

trait ClassTagHelperMemberInfoGetter {
  this : ClassTagHelperConcreteImplementation =>
  def getMembersInfo[T](clazz : Class[T]) : ClassMembersInfoModel = {
    val classT = clazz.getClass

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

    val clx = ClassMembersInfoImplementationModel(
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
