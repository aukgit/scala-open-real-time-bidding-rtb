package shared.io.helpers

import shared.com.ortb.constants.AppConstants
import shared.io.loggers.AppLogger

import scala.reflect.runtime.universe._
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
      val classEntity = item.get.getClass
      return classEntity.getTypeName.replace("$",AppConstants.Dot)
    } catch {
      case e : Exception => AppLogger.error(e)
    }

    ""
  }

  def isIntegerType[T]()(implicit T : ru.TypeTag[T]) : Boolean = {
    val typeOfKey = typeOf[T]
    typeOfKey match {
      case i if i =:= typeOf[Int] =>
        return true
    }

    false
  }

  def isStringType[T]()(implicit T : TypeTag[T]) : Boolean = {
    val typeOfKey = typeOf[T]
    typeOfKey match {
      case i if i =:= typeOf[String] =>
        return true
    }

    false
  }

//  def isTypeOf[T, TCastingType](implicit T : ru.TypeTag[T], TCastingType: ru.TypeTag[T]) : Boolean = {
//    val typeOfKey = typeOf[T]
//    typeOfKey match {
//      case i if i =:= typeOf[TCastingType] =>
//        return true
//    }
//
//    false
//  }
}

