package shared.io.helpers

import shared.io.helpers.implementation.NiceObject
import shared.io.helpers.traits._
import shared.io.helpers.traits.reflection.{ ClassTagHelper, ReflectionHelperBase }


object ReflectionHelper extends ReflectionHelperBase {
  implicit def toNiceObject[T <: AnyRef](x : T) : NiceObject[T] = new NiceObject(x)
}

