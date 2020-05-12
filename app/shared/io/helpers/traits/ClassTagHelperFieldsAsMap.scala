package shared.io.helpers.traits

import java.lang.reflect.Field

import shared.io.helpers.{ ClassTagHelperConcreteImplementation, EmptyValidateHelper }

import scala.reflect.ClassTag

trait ClassTagHelperFieldsAsMap {
  this : ClassTagHelperConcreteImplementation =>

  def getFieldsAsMap[T](implicit ct : ClassTag[T]) : Map[String, Field] = {
    val fields = getFields[T]
    val length = fields.length
    if (EmptyValidateHelper.isItemsEmptyDirect(fields)) {
      return Map.empty
    }

    val map = new collection.mutable.HashMap[String, Field](length + 1, 1.2)
    fields.foreach(f => map.addOne(f.getName -> f))

    map.toMap
  }
}
