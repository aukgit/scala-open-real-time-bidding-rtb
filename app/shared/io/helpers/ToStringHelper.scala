package shared.io.helpers

import shared.io.loggers.AppLogger

object ToStringHelper {
  def toStringOf(value : Option[Any]) : Option[String] = {
    if (EmptyValidateHelper.isEmpty(value)) {
      return None
    }

    try {
      return Some(value.get.toString)
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
      return Some(items.get.mkString(join))
    }
    catch {
      case e : Exception =>
        AppLogger.error(e)
        AppLogger.logEntitiesNonFuture(isExecute = true, items)
    }

    None
  }
}
