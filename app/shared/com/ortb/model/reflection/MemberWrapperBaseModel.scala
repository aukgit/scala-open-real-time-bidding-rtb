package shared.com.ortb.model.reflection

import java.lang.reflect.Member

import shared.com.ortb.model.reflection.implementations.ModifierEnhancementConcreteImplementation

abstract class MemberWrapperBaseModel {
  val member : Member
  lazy val modifiers = new ModifierEnhancementConcreteImplementation(member.getModifiers)
  lazy val name : String = member.getName
}
