package shared.com.ortb.model.reflection

import java.lang.reflect.Member

abstract class MemberWrapperBaseModel {
  val member : Member
  lazy val modifiers = new ModifierEnhancementConcreteImplementation(member.getModifiers)
  lazy val name : String = member.getName
}

class MemberWrapperConcreteModel(
  memberIn : Member) extends MemberWrapperBaseModel {
  lazy override val member : Member = memberIn
}
