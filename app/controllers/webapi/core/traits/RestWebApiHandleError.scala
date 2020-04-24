package controllers.webapi.core.traits

trait RestWebApiHandleError[TTable, TRow, TKey] {
  def handleError(
    exception : Exception, controllerGenericActionWrapper : ControllerGenericActionWrapper
  ) : Result
}
