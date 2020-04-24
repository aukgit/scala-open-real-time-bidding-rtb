package shared.com.ortb.model.wrappers.http

import play.api.mvc.Headers
import shared.com.ortb.enumeration.HttpActionWrapperType.HttpActionWrapperType
import shared.com.ortb.model.wrappers.persistent.EntityWrapperWithOptions

abstract class HttpActionWrapperBase[TRow, TKey](
  methodName : Option[String] = None,
  lineNumber : Option[Int] = None,
  entityWrapper : Option[EntityWrapperWithOptions[TRow, TKey]] = None,
  controllerGenericActionWrapper : Option[ControllerGenericActionWrapper] = None,
  headers : Option[Seq[Headers]] = None
)
