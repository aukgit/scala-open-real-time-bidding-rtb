package shared.com.ortb.model.reflection

import java.lang.reflect.Member

abstract class MemberWrapperModel(
  val member : Member) {
  lazy val modifiers = new ModifierEnhancementConcreteImplementation(member.getModifiers)
  lazy val name : String = member.getName
}
