package shared.io.helpers

import java.lang.reflect.Field

import shared.com.ortb.constants.AppConstants
import shared.io.loggers.AppLogger

import scala.reflect.ClassTag
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
      return classEntity.getTypeName.replace("$", AppConstants.Dot)
    } catch {
      case e : Exception => AppLogger.error(e)
    }

    ""
  }

  def isIntegerType[T]()(implicit T : ru.TypeTag[T]) : Boolean = {
    val typeOfKey = typeOf[T]
    return typeOfKey match {
      case i if i =:= typeOf[Int] =>
        return true
    }

    false
  }

  def isStringType[T]()(implicit T : TypeTag[T]) : Boolean = {
    val typeOfKey = typeOf[T]
    return typeOfKey match {
      case i if i =:= typeOf[String] =>
        return true
    }

    false
  }

  def isTypeOf[T : ru.TypeTag, TCastingType : ru.TypeTag] : Boolean = {
    val typeOfKey = typeOf[T]
    return typeOfKey match {
      case i if i =:= typeOf[TCastingType] =>
        return true
    }

    false
  }

  def getCaseClassFields[T : TypeTag] : Iterable[MethodSymbol] = typeOf[T].members.collect {
    case m : MethodSymbol if m.isCaseAccessor => m
  }

  def getCaseClassFieldsNames[T <: Class[T]] : Iterable[Field] = {
    classOf[T]
      .getDeclaredFields
  }

  class NiceObject[T <: AnyRef](x : T) {
    def niceClass : Class[_ <: T] = x.getClass.asInstanceOf[Class[T]]
  }

  implicit def toNiceObject[T <: AnyRef](x : T) : NiceObject[T] = new NiceObject(x)
}

