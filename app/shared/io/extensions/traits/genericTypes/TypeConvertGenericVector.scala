package shared.io.extensions.traits.genericTypes

trait TypeConvertGenericVector[T] extends TypeConvertGenericIterable[T] {
  lazy val anyItems : Iterable[T] = vector
  protected val vector : Vector[T]
  lazy override val toSome : Option[Vector[T]] = Some(vector)
}
