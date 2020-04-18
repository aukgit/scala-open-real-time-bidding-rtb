package shared.com.ortb.adapters.traits

trait BasicAdapter {
  def convertItemTo[A, B](
    item : Option[A],
    adapterLogic : PartialFunction[Option[A], Option[B]]) : Option[B]

  def convertItemsTo[A, B](
    items : Option[Iterable[A]],
    adapterLogic :
    PartialFunction[Option[Iterable[A]], Option[Iterable[B]]]) :
  Option[Iterable[B]]
}


