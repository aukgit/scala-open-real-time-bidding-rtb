package controllers.webapi.core

import play.api.mvc.Result
import shared.com.ortb.model.wrappers.http._

trait RestWebApiResponsePerform[TTable, TRow, TKey] {
  def OkJson(jsonString : String) : Result

  def performBadResponse(
    httpFailedActionWrapper : Option[HttpFailedActionWrapper[TRow, TKey]] = None)
  : Result

  def performBadResponseOnException(
    httpFailedActionWrapper : HttpFailedExceptionActionWrapper[TRow, TKey])
  : Result

  def performOkayOnEntity(
    httpSuccessActionWrapper : Option[HttpSuccessActionWrapper[TRow, TKey]] = None)
  : Result

  def performOkay(
    httpSuccessActionWrapper : Option[HttpSuccessActionWrapper[TRow, TKey]] = None)
  : Result
}
