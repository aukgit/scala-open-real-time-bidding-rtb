package shared.com.ortb.webapi.traits

import play.api.mvc.Result
import shared.com.ortb.model.wrappers.http._

trait RestWebApiPerformAction[TTable, TRow, TKey] {
  def OkJson(jsonString : String) : Result

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
