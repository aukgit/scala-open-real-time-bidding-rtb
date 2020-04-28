package shared.com.ortb.model.results

import com.sun.tools.javac.code.TypeTag
import shared.com.ortb.model.attributes.GenericResponseAttributesModel
import shared.com.ortb.model.wrappers.persistent.EntityWrapper
import shared.io.helpers.ReflectionHelper
import scala.reflect.runtime.universe._

case class RepositoryOperationResultModel[TRow, TKey](
  attributes : Option[GenericResponseAttributesModel],
  data : Option[EntityWrapper[TRow, TKey]] = None) {
  def getIdAs[T : TypeTag] : Option[T] = {
    if (attributes.isEmpty) {
      return None
    }

    if (ReflectionHelper.isIntegerType[T]) {
      return Some(attributes.get.id.get.toInt.asInstanceOf[T])
    } else if (ReflectionHelper.isStringType[T]) {
      return Some(attributes.get.id.get.asInstanceOf[T])
    }else {
      throw new NotImplementedError("Type can be either string or integer for T")
    }
  }

  def getIdAsInt : Option[Int] = {
    getIdAs[Int](typeOf(Int))
  }
}
