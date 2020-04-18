package shared.com.ortb.webapi.traits

import play.api.mvc.{Request, Result}
import shared.com.ortb.enumeration.DatabaseActionType.DatabaseActionType
import shared.com.ortb.model.wrappers.http._

trait RestWebApiMessages[TTable, TRow, TKey] {
  val entityCreateFailedMessage = "Entity failed to create"
  val entityCreateSuccessMessage = "Entity create successful"

  def failedMessage(
    databaseActionType   : Option[DatabaseActionType] = None,
    entity               : Option[TRow] = None,
    additionalMessage : String = "") : String

  def successMessage(
    databaseActionType    : Option[DatabaseActionType] = None,
    entity                : Option[TRow] = None,
    additionalMessage     : String = "") : String

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
