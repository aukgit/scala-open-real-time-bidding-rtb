package shared.com.ortb.model

import java.lang.reflect._

case class MethodWrapperModel(
  method : Method
)
  extends MemberWrapperModel(method) {
  lazy val parameters = method.getParameters
  lazy val genericParameters = method.getGenericParameterTypes
  lazy val returnType : Class[_] = method.getReturnType
  lazy val parameterCount : Int = method.getParameterCount
}
