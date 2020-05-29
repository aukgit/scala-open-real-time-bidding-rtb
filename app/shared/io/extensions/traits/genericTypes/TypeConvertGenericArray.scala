package shared.io.extensions.traits.genericTypes

trait TypeConvertGenericArray[T] extends TypeConvertGenericIterable[T] {
  lazy protected val anyItems : Iterable[T] = array.toIterable
  protected val array : Array[T]
  lazy val toSomeArray : Option[Array[T]] = Some(array)
}
