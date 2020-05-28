package shared.io.extensions.traits.genericTypes

trait TypeConvertGenericList[T] extends TypeConvertGenericIterable[T] {
  lazy val anyItems : Iterable[T] = list
  protected val list : List[T]
  lazy override val toSome : Option[List[T]] = Some(list)
}
