package shared.com.ortb.model.wrappers.http

import play.api.mvc.Headers
import shared.com.ortb.model.wrappers.persistent.EntityWrapperWithOptions

abstract class HttpActionWrapperBase[TRow, TKey] {
  val methodName : Option[String]
  val lineNumber : Option[Int]
  val entityWrapper : Option[EntityWrapperWithOptions[TRow, TKey]]
  val controllerGenericActionWrapper : ControllerGenericActionWrapper
  val headers : Option[Seq[Headers]]
}
