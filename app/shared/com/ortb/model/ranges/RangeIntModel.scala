package shared.com.ortb.model.ranges

import scala.util.Random

case class RangeIntModel(
  start : Int,
  end : Int
) extends RangeModel[Int] {
  /**
   * Get a static random number inclusive of start and exclusive of end
   */
  lazy override val staticRandomInBetweenRange : Int = randomInBetweenRange

  /**
   * Get a random number inclusive of start and exclusive of end
   */
  override def randomInBetweenRange : Int = Random.between(
    start,
    end)
}
