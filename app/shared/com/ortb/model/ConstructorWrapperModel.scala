package shared.com.ortb.model

import java.lang.reflect.Constructor

case class ConstructorWrapperModel(
  constructor : Constructor[_])
  extends MemberWrapperModel(constructor)
