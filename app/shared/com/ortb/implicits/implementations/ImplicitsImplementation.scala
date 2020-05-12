package shared.com.ortb.implicits.implementations

import shared.com.ortb.implicits.DynamicInvoker

object ImplicitsImplementation {
  /**
   * Any ref method caller
   * Reference: https://bit.ly/2RCprcz
   *
   * @param klass
   * @tparam T
   *
   * @return
   */
  implicit def anyRefCaller[T >: Null <: AnyRef](klass : T) : DynamicInvoker[T] = DynamicInvoker(klass)
}
