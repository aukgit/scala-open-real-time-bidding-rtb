package shared.com.ortb.model.ranges

import scala.util.Random

case class RangeFloatModel(
  start : Float,
  end : Float
) extends RangeModel[Float] {
  /**
   * Get a static random number inclusive of start and exclusive of end
   */
  lazy override val staticRandomInBetweenRange : Float =
    randomInBetweenRange

  /**
   * Get a random number inclusive of start and exclusive of end
   */
  override def randomInBetweenRange : Float = Random.between(
    start,
    end)
}
