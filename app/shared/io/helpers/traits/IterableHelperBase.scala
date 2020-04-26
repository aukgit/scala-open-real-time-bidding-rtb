package shared.io.helpers.traits

trait IterableHelperBase {
  def toIterableStrings[T](iterable : Option[Iterable[T]]) : Option[Iterable[String]]

  def toSeqStrings[T](iterable : Option[Iterable[T]]) : Option[Seq[String]]

  def toListStrings[T](iterable : Option[Iterable[T]]) : Option[List[String]]

  def toArrayStrings[T](iterable : Option[Iterable[T]]) : Option[Array[String]]
}
