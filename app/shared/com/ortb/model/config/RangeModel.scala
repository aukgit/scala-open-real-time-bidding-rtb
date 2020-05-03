package shared.com.ortb.model.config

import scala.util.Random

case class RangeModel(
  start: Double,
  end: Double
) {
  /**
   * Get a number inclusive of start and exclusive of end
   */
  lazy val guessRandomInBetween : Double = {
    Random.between(
      start,
      end)
  }
}
