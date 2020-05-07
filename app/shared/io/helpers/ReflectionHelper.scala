package shared.io.helpers

import shared.io.helpers.traits._

class ClassTagHelperBaseConcreteImplementation extends ClassTagHelperBase

object ReflectionHelper extends ReflectionHelperBase {
  implicit def toNiceObject[T <: AnyRef](x : T) : NiceObject[T] = new NiceObject(x)
}

