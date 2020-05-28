package shared.com.ortb.model.ranges

abstract class RangeModel[T : Numeric] {
  val start : T
  val end : T
  /**
   * Get a number inclusive of start and exclusive of end
   */
  val randomInBetweenRange : T
}
