package shared.io.helpers

import shared.io.loggers.AppLogger

object ToStringHelper {
  def toStringOf(value : Option[Any]) : Option[String] = {
    if (EmptyValidateHelper.isEmpty(value)) {
      return None
    }

    try {
      val typeName = ReflectionHelper.getTypeName(value)
      return Some(s"$typeName : ${value.get.toString}")
    }
    catch {
      case e : Exception =>
        AppLogger.error(e, "Failed at [toStringOf]")
        AppLogger.logNonFutureNullable("Failed to make toString of :", value)
    }

    None
  }

  def toStringOfItems(
    items : Option[Iterable[Any]],
    join : String = ",\n") : Option[String] = {
    if (EmptyValidateHelper.isItemsEmpty(items)) {
      return None
    }

    try {
      val typeName = ReflectionHelper.getTypeName(Some(items.get.head))
      val toStringAll = items.get.mkString(join)

      return Some(s"$typeName :\n ${toStringAll}")
    }
    catch {
      case e : Exception =>
        AppLogger.error(e)
        AppLogger.logEntitiesWithCondition(isExecute = true, items)
    }

    None
  }
}
