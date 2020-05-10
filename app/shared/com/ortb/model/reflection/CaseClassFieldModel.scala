package shared.com.ortb.model.reflection

import shared.com.ortb.types.BasicTypeTester

case class CaseClassFieldModel(
  name : String,
  value : Any,
  index : Int) {
  lazy val typeTester = new BasicTypeTester(value)
  def toStringField : String = {
    val typeClassName= typeTester.typeClassName()

    s"${name} : ${typeClassName}"
  }
}
