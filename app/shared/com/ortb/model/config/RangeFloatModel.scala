package shared.com.ortb.model.config

import scala.util.Random

case class RangeFloatModel(
  start : Float,
  end : Float
) extends RangeModel[Float] {
  /**
   * Get a number inclusive of start and exclusive of end
   */
  lazy override val guessRandomInBetween : Float = Random.between(
    start,
    end)
}
