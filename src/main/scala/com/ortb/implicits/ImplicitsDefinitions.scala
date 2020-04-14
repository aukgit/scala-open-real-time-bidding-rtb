package com.ortb.implicits

object ImplicitsDefinitions {
  /**
   * Any ref method caller
   * Reference: https://bit.ly/2RCprcz
   * @param klass
   * @tparam T
   * @return
   */
  implicit def anyRefCaller[T >: Null <: AnyRef](klass: T): DynamicInvoker[T] = DynamicInvoker(klass)
}
