package shared.com.ortb.model.reflection

import java.lang.reflect.Constructor

case class ConstructorWrapperModel(
  constructor : Constructor[_])
  extends MemberWrapperModel(constructor)
