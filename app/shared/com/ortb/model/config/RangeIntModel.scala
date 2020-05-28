package shared.com.ortb.model.config

import scala.util.Random

case class RangeIntModel(
  start : Int,
  end : Int
) extends RangeModel[Int] {
  /**
   * Get a number inclusive of start and exclusive of end
   */
  lazy override val randomInBetweenRange : Int = Random.between(
    start,
    end)
}
