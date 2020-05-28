package shared.com.ortb.model.config

import scala.util.Random

case class RangeLongModel(
  start : Long,
  end : Long
) extends RangeModel[Long] {
  /**
   * Get a number inclusive of start and exclusive of end
   */
  lazy override val guessRandomInBetween : Long = Random.between(
    start,
    end)
}
