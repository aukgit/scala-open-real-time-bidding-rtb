package shared.com.ortb.model.ranges

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
