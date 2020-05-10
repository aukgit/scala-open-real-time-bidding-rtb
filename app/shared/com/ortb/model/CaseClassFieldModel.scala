package shared.com.ortb.model

import shared.com.ortb.BasicTypeTester

import scala.reflect.runtime.{ universe => ru }

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
