package shared.io.helpers

import com.github.dwickern.macros.NameOf._
import shared.io.loggers.AppLogger

object ToStringHelper {
  def safeJoin(
    items : Option[Iterable[String]],
    joiner : String) : String = {
    if (EmptyValidateHelper.isItemsEmpty(items)) {
      return ""
    }

    items.get.mkString(joiner)
  }

  def toStringOf(value : Option[Any]) : Option[String] = {
    if (EmptyValidateHelper.isEmpty(value)) {
      return None
    }

    val methodName = nameOf(toStringOf _)

    try {
      val typeName = ReflectionHelper.getTypeName(value)
      return Some(s"$typeName : ${ value.get.toString }")
    }
    catch {
      case e : Exception =>
        AppLogger.error(e, s"Failed at [${ methodName }]")
        AppLogger.logMaybeItem("Failed to make toString of :", value)
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

      return Some(s"$typeName :\n ${ toStringAll }")
    }
    catch {
      case e : Exception =>
        AppLogger.error(e)
        AppLogger.logEntitiesWithCondition(isExecute = true, items)
    }

    None
  }
}
