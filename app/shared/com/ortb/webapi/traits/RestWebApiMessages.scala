package shared.com.ortb.webapi.traits

import play.api.mvc.Result
import shared.com.ortb.model.wrappers.http._

trait RestWebApiMessages[TTable, TRow, TKey] {
  val entityCreateFailedMessage = "Entity failed to create"
  val entityCreateSuccessMessage = "Entity create successful"

  def getDefaultFailedMessage(
    controllerGenericActionWrapper : Option[ControllerGenericActionWrapper] = None,
    entity : Option[TRow] = None,
    message              : String = "") : String

  def getDefaultSuccessMessage(
    controllerGenericActionWrapper : Option[ControllerGenericActionWrapper] = None,
    entity : Option[TRow] = None,
    message              : String = "") : String

  def performBadRequest(
    httpFailedActionWrapper : Option[HttpFailedActionWrapper[TRow, TKey]] = None)
  : Result

  def performBadRequestOnException(
    httpFailedActionWrapper : HttpFailedExceptionActionWrapper[TRow, TKey])
  : Result

  def performOkayOnEntity(
    httpSuccessActionWrapper : Option[HttpSuccessActionWrapper[TRow, TKey]] = None)
  : Result

  def performOkay(
    httpSuccessActionWrapper : Option[HttpSuccessActionWrapper[TRow, TKey]] = None)
  : Result
}
