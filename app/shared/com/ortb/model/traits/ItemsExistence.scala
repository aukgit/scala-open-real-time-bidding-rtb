package shared.com.ortb.model.traits

trait ItemsExistence[T] {
  val hasItem : Boolean
  val iterable : Iterable[T]
}
