package shared.io.extensions.traits.genericTypes

trait TypeConvertGenericArray[T] extends TypeConvertGenericIterable[T] {
  lazy val anyItems : Iterable[T] = array.toIterable
  protected val array : Array[T]
}
