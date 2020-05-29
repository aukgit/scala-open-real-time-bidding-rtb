package shared.com.ortb.model.ranges

abstract class RangeModel[T : Numeric] {
  val start : T
  val end : T

  /**
   * Get a static random number inclusive of start and exclusive of end
   */
  val staticRandomInBetweenRange : T

  /**
   * Get a random number inclusive of start and exclusive of end
   */
  def randomInBetweenRange : T
}
