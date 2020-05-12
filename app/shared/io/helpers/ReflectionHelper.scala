package shared.io.helpers

import shared.io.helpers.traits._

class ClassTagHelperConcreteImplementation extends ClassTagHelper

object ReflectionHelper extends ReflectionHelperBase {
  implicit def toNiceObject[T <: AnyRef](x : T) : NiceObject[T] = new NiceObject(x)
}

