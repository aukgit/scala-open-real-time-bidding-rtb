package io

import scala.reflect.runtime.{universe => ru}

object ReflectionHelper {
  def getTypeTag[T : ru.TypeTag](obj : T) : ru.TypeTag[T] =
    ru.typeTag[T]

  def getType[T : ru.TypeTag](obj : T) : ru.Type = {
    val typeTag = getTypeTag(obj)
    typeTag.tpe
  }

  def getTypeName[T : ru.TypeTag](obj : T) : String =
    getType(obj).toString
}
