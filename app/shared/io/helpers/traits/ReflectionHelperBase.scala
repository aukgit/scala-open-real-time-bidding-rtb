package shared.io.helpers.traits

import shared.com.ortb.constants.AppConstants
import shared.com.ortb.model.reflection
import shared.com.ortb.model.reflection.{ CaseClassFieldModel, CaseClassInfoModel }
import shared.io.helpers._
import shared.io.loggers.AppLogger

import scala.reflect.runtime.universe._
import scala.reflect.runtime.{ universe => ru }

trait ReflectionHelperBase {
  def getTypeTag[T : ru.TypeTag] : ru.TypeTag[T] =
    ru.typeTag[T]

  /**
   * reference : https://bit.ly/3evRZy7
   *
   * @param givenClass : Given class type
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
    return typeOf[T] match {
      case i if i =:= typeOf[Int] =>
        return true
    }

    false
  }

  def isStringType[T]()(implicit T : TypeTag[T]) : Boolean = {
    return typeOf[T] match {
      case i if i =:= typeOf[String] =>
        return true
    }

    false
  }

  def isTypeOf[T : ru.TypeTag, TCastingType : ru.TypeTag] : Boolean = {
    return typeOf[T] match {
      case i if i =:= typeOf[TCastingType] =>
        return true
    }

    false
  }

  def getFieldsAsMethodSymbol[T : TypeTag] : Iterable[MethodSymbol] = typeOf[T].members.collect {
    case m : MethodSymbol if m.isCaseAccessor => m
  }

  def getCaseClassInfoModel(caseModel : Any) : Option[CaseClassInfoModel] = {
    val maybeProduct = CastingHelper.toProduct(caseModel)
    if (maybeProduct.isEmpty) {
      return None
    }

    val product = maybeProduct.get

    if (product.productArity == 0) {
      Some(CaseClassInfoModel(List.empty))
    }

    val array = new Array[CaseClassFieldModel](product.productArity)
    val it = product.productIterator
    var i = 0

    while (it.hasNext) {
      val name = product.productElementName(i)
      val value = it.next()
      array(i) = CaseClassFieldModel(name, value, i)
      i += 1
    }

    Some(reflection.CaseClassInfoModel(array.toList))
  }

  def getFieldsNamesOfCaseModel(caseModel : Any) : Option[Iterable[String]] = {
    val maybeProduct = CastingHelper.toProduct(caseModel)
    if (maybeProduct.isEmpty) {
      return None
    }

    val product = maybeProduct.get

    val array = new Array[String](product.productArity)
    val elements = product.productElementNames
    var i = 0

    while (elements.hasNext) {
      array(i) = elements.next
      i += 1
    }

    Some(array)
  }

  lazy val classTagHelper : ClassTagHelperBase = new ClassTagHelperBaseConcreteImplementation
}
