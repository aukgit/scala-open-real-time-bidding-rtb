package shared.com.ortb.model.config

import scala.util.Random

case class RangeDoubleModel(
  start : Double,
  end : Double
) extends RangeModel[Double] {
  /**
   * Get a number inclusive of start and exclusive of end
   */
  lazy override val randomInBetweenRange : Double = Random.between(
    start,
    end)
}
