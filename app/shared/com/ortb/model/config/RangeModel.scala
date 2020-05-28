package shared.com.ortb.model.config

abstract class RangeModel[T : Numeric] {
  val start : T
  val end : T
  /**
   * Get a number inclusive of start and exclusive of end
   */
  val guessRandomInBetween : T
}
