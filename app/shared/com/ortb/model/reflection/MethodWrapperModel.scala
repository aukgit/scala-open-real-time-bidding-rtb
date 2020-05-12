package shared.com.ortb.model.reflection

import java.lang.reflect.{ Method, Parameter, Type }

case class MethodWrapperModel(
  method : Method
)
  extends MemberWrapperConcreteModel(method) {
  lazy val parameters : Array[Parameter] = method.getParameters
  lazy val genericParameters : Array[Type] = method.getGenericParameterTypes
  lazy val returnType : Class[_] = method.getReturnType
  lazy val parameterCount : Int = method.getParameterCount
}
