package shared.com.ortb.model.ranges

import scala.util.Random

case class RangeIntModel(
  start : Int,
  end : Int
) {
  /**
   * Get a number inclusive of start and exclusive of end
   */
  lazy val randomInBetweenRange : Int = Random.between(
    start,
    end)
}
