package shared.io.extensions.traits.genericTypes

trait TypeConvertStringArray extends TypeConvertStringIterable {
  protected val array : Array[String]
  protected val anyItems : Iterable[String] = array.toIterable
}
