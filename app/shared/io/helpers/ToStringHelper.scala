package shared.io.helpers

import shared.io.loggers.AppLogger

object ToStringHelper {
  def toStringOf(value : Option[Any]) : Option[String] = {
    if(EmptyValidateHelper.isEmpty(value)){
      return None
    }

    Some(value.get.toString)
  }

  def toStringOfItems(
    items : Option[Iterable[Any]],
    join : String = ",\n") : Option[String] = {
    if(EmptyValidateHelper.isItemsEmpty(items)){
      return None
    }

    try{
      Some(items.get.mkString(join))
    }
    catch {
      case e:Exception =>
        AppLogger.error(e)
        AppLogger.logEntitiesNonFuture(items)
    }

  }
}
