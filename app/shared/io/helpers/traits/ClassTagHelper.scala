package shared.io.helpers.traits

import java.lang.reflect.Member

import shared.com.ortb.enumeration.ReflectionModifier
import shared.com.ortb.manager.traits.CreateDefaultContext
import shared.io.helpers.ClassTagHelperConcreteImplementation

import scala.reflect.ClassTag

trait ClassTagHelper
  extends CreateDefaultContext
    with ClassTagHelperProperties
    with ClassTagHelperMethods
    with ClassTagHelperFields
    with ClassTagHelperMembers
    with ClassTagHelperConstructors {
  this : ClassTagHelperConcreteImplementation =>

  def getClass[T](implicit ct : ClassTag[T]) : Class[_] =
    ct.runtimeClass

  def getFilterMembersWithModifier[T](
    members : Iterable[Member],
    reflectionModifier : ReflectionModifier)(implicit ct : ClassTag[T]) : Iterable[Member] =
    members.filter(member => reflectionModifier.value == member.getModifiers)
}
