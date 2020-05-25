package shared.com.ortb.webapi.core.traits

import play.api.mvc._
import shared.com.ortb.model.wrappers.http.ControllerGenericActionWrapper

trait RestWebApiHandleError[TTable, TRow, TKey] {
  def handleError(
    exception : Exception, controllerGenericActionWrapper : ControllerGenericActionWrapper
  ) : Result
}
