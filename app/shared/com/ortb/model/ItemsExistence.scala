package shared.com.ortb.model

trait ItemsExistence[T] {
  val hasItem : Boolean
  val iterable : Iterable[T]
}
