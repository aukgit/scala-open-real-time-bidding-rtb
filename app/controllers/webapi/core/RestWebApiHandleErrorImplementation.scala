package controllers.webapi.core

import play.api.mvc.Result
import shared.com.ortb.model.wrappers.http.{ ControllerGenericActionWrapper, HttpFailedExceptionActionWrapper }
import shared.io.loggers.AppLogger

trait RestWebApiHandleErrorImplementation[TTable, TRow, TKey]
  extends RestWebApiHandleError[TTable, TRow, TKey] {
  this : AbstractRestWebApi[TTable, TRow, TKey] =>
  def handleError(
    exception : Exception,
    controllerGenericActionWrapper : ControllerGenericActionWrapper
  ) : Result = {
    AppLogger.error(exception)
    val httpFailedExceptionActionWrapper =
      HttpFailedExceptionActionWrapper[TRow, TKey](
        exception = Some(exception),
        controllerGenericActionWrapper = controllerGenericActionWrapper
      )

    performBadRequestOnException(httpFailedExceptionActionWrapper)
    throw exception
  }
}
