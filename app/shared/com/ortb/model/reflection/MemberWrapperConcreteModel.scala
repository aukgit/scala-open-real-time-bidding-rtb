package shared.com.ortb.model.reflection

import java.lang.reflect.Member

class MemberWrapperConcreteModel(
  memberIn : Member) extends MemberWrapperBaseModel {
  lazy override val member : Member = memberIn
}
