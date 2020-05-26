package shared.io.extensions.traits.primitiveTypes

import shared.com.ortb.model.results.ResultWithSuccessModel
import shared.io.helpers.{ EmptyValidateHelper, NumberHelper }

trait TypeConvertOptionString {
  lazy val hasCharacter : Boolean = EmptyValidateHelper.isOptionStringDefined(s)
  lazy val isEmpty : Boolean = !hasCharacter
  lazy val getDefinedOrEmptyString : String = if (hasCharacter) s.get else ""
  lazy val toIntSome : Option[Int] = if (hasCharacter) s.get.toIntOption else None
  lazy val toDoubleSome : Option[Double] = if (hasCharacter) s.get.toDoubleOption else None
  lazy val toFloatSome : Option[Float] = if (hasCharacter) s.get.toFloatOption else None
  lazy val getIntResult : ResultWithSuccessModel[Int] = NumberHelper.getIntResult(self)
  lazy val getDoubleResult : ResultWithSuccessModel[Double] = NumberHelper.getDoubleResult(self)
  lazy val getFloatResult : ResultWithSuccessModel[Float] = NumberHelper.getFloatResult(self)
  protected val self : String = if (hasCharacter) s.get else ""

  def toInt(default : Int = 0) : Int = if (getIntResult.isSuccess) getIntResult.result.get else default

  def toDouble(default : Double = 0) : Double = if (getDoubleResult.isSuccess) getDoubleResult.result.get else default

  def toFloat(default : Float = 0) : Float = if (getFloatResult.isSuccess) getFloatResult.result.get else default

  protected val s : Option[String]
}
