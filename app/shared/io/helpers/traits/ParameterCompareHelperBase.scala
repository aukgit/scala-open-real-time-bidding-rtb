package shared.io.helpers.traits

import java.lang.reflect.Parameter

trait ParameterCompareHelperBase {
  def isParameterTypeEquivalent(parameter1 : Parameter, parameter2 : Parameter) : Boolean = {
    parameter1.getType.toString == parameter2.getType.toString
    ???
  }

  def isParametersEquivalent(parameter1 : Array[Parameter], parameter2 : Array[Parameter]) : Boolean = {
    // parameter1.getType.toString == parameter2.getType.toString
    ???
  }
}

