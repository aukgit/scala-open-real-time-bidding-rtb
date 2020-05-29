package shared.com.ortb.model.ranges

import scala.util.Random

case class RangeLongModel(
  start : Long,
  end : Long
) extends RangeModel[Long] {
  /**
   * Get a static random number inclusive of start and exclusive of end
   */
  lazy override val staticRandomInBetweenRange : Long = randomInBetweenRange

  /**
   * Get a random number inclusive of start and exclusive of end
   */
  override def randomInBetweenRange : Long = Random.between(
    start,
    end)
}
