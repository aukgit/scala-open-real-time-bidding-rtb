package shared.io.helpers

import shared.io.helpers.traits.IterableHelperBase

object IterableHelper extends IterableHelperBase {
  def toIterableStrings[T](iterable : Option[Iterable[T]]) : Option[Iterable[String]] = {
    if (EmptyValidateHelper.isItemsEmpty(iterable)) {
      return None
    }

    val items = iterable.get.map(item => item.toString)

    Some(items)
  }

  def toSeqStrings[T](iterable : Option[Iterable[T]]) : Option[Seq[String]] = {
    if (EmptyValidateHelper.isItemsEmpty(iterable)) {
      return None
    }

    val items = iterable.get.map(item => item.toString)

    Some(items.toSeq)
  }

  def toListStrings[T](iterable : Option[Iterable[T]]) : Option[List[String]] = {
    if (EmptyValidateHelper.isItemsEmpty(iterable)) {
      return None
    }

    val items = iterable.get.map(item => item.toString)

    Some(items.toList)
  }

  def toArrayStrings[T](iterable : Option[Iterable[T]]) : Option[Array[String]] = {
    if (EmptyValidateHelper.isItemsEmpty(iterable)) {
      return None
    }

    val items = iterable.get.map(item => item.toString)

    Some(items.toArray)
  }
}
