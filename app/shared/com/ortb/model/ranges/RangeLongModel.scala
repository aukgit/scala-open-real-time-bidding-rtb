package shared.com.ortb.model.ranges

import scala.util.Random

case class RangeLongModel(
  start : Long,
  end : Long
) {
  /**
   * Get a number inclusive of start and exclusive of end
   */
  lazy val randomInBetweenRange : Long = Random.between(
    start,
    end)
}
