package shared.com.ortb.model.ranges

import scala.util.Random

case class RangeDoubleModel(
  start : Double,
  end : Double
) {
  /**
   * Get a number inclusive of start and exclusive of end
   */
  lazy val randomInBetweenRange : Double = Random.between(
    start,
    end)
}
