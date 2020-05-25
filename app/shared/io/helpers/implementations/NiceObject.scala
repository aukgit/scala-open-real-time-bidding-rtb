package shared.io.helpers.implementations

class NiceObject[T <: AnyRef](x : T) {
  def niceClass : Class[_ <: T] = x.getClass.asInstanceOf[Class[T]]
}
