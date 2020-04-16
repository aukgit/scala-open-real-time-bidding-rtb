package shared.com.ortb.implicits

/**
 * Reference: https://bit.ly/2RCprcz
 *
 * @param klass
 * @tparam T
 */
case class DynamicInvoker[T >: Null <: AnyRef](klass : T) {
  def call(methodName : String, args : AnyRef*) : AnyRef = {
    def argTypes = args.map(_.getClass)

    def method = klass.getClass.getMethod(methodName, argTypes : _*)

    method.invoke(klass, args : _*)
  }
}