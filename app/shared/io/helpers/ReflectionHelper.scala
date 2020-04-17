package shared.io.helpers

import scala.reflect.runtime.{universe => ru}

object ReflectionHelper {
  def getTypeTag[T : ru.TypeTag] : ru.TypeTag[T] =
    ru.typeTag[T]

  /**
   * reference : https://bit.ly/3evRZy7
   * @param givenClass
   * @tparam T
   * @return
   */
  def getType[T](givenClass : Class[T]) : ru.Type = {
    val runtimeMirror = ru.runtimeMirror(givenClass.getClassLoader)
    runtimeMirror.classSymbol(givenClass).toType
  }

  def getTypeName[T](item : Option[T]) : String = {
    if (item.isEmpty) {
      return ""
    }

    item.get.getClass.getTypeName
  }
}

