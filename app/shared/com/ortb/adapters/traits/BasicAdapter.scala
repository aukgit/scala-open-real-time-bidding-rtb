package shared.com.ortb.adapters.traits

trait BasicAdapter {
  def convertItemTo[A, B](
    item         : Option[A],
    adapterLogic : (Option[A]) => Option[B]) : Option[B]

  def convertItemsTo[A, B](
    items        : Option[Iterable[A]],
    adapterLogic : Option[Iterable[A]] => Option[Iterable[B]]) :
  Option[Iterable[B]]
}



