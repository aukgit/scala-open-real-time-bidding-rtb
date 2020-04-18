package shared.com.ortb.adapters.traits

trait BasicAdapterWithPartialFunction[A,B] extends BasicAdapter {
  def fromTo(itemInput : Option[A]) : Option[B]

  def fromManyTo(itemsInput : Option[Iterable[A]]) : Option[Iterable[B]]
}
