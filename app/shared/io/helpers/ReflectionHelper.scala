package shared.io.helpers

import shared.io.loggers.AppLogger

import scala.reflect.runtime.{ universe => ru }

object ReflectionHelper {
  def getTypeTag[T : ru.TypeTag] : ru.TypeTag[T] =
    ru.typeTag[T]

  /**
   * reference : https://bit.ly/3evRZy7
   *
   * @param givenClass
   * @tparam T
   *
   * @return
   */
  def getType[T](givenClass : Class[T]) : ru.Type = {
    try {
      val runtimeMirror = ru.runtimeMirror(givenClass.getClassLoader)
      return runtimeMirror.classSymbol(givenClass).toType
    } catch {
      case e : Exception => AppLogger.error(e)
    }

    null
  }

  def getTypeName[T](item : Option[T]) : String = {
    if (item.isEmpty) {
      return ""
    }

    try {
      return item.get.getClass.getTypeName
    } catch {
      case e : Exception => AppLogger.error(e)
    }

    ""
  }
}

