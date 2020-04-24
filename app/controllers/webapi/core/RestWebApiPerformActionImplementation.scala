package controllers.webapi.core

import play.api.mvc._
import play.mvc.Http.MimeTypes
import shared.com.ortb.model.wrappers.http._

trait RestWebApiPerformActionImplementation[TTable, TRow, TKey]
  extends RestWebApiPerformAction[TTable, TRow, TKey] {
  this : AbstractRestWebApi[TTable, TRow, TKey] =>

  def performBadRequestAsAction(
    httpFailedActionWrapper : Option[HttpFailedActionWrapper[TRow, TKey]]) : Action[AnyContent] = {
    if (httpFailedActionWrapper.isDefined) {
      components.actionBuilder(BadRequest(httpFailedActionWrapper.toString))
    } else {
      components.actionBuilder(BadRequest(getDefaultFailedMessage()))
    }
  }

  def performBadRequestOnException(
    httpFailedActionWrapper : HttpFailedExceptionActionWrapper[TRow, TKey]) : Result =
    BadRequest(httpFailedActionWrapper.toString)

  def performOkayOnEntity(
    httpSuccessActionWrapper : Option[HttpSuccessActionWrapper[TRow, TKey]]) : Result = {
    if (httpSuccessActionWrapper.isDefined) {
      return Ok(httpSuccessActionWrapper.toString)
    }

    Ok(getDefaultSuccessMessage())
  }

  def performOkay(
    httpSuccessActionWrapper : Option[HttpSuccessActionWrapper[TRow, TKey]]) : Result = {
    if (httpSuccessActionWrapper.isDefined) {
      return Ok(httpSuccessActionWrapper.toString)
    }

    Ok(getDefaultSuccessMessage())
  }

  override def OkJson(jsonString : String): Result = Ok(jsonString).as(MimeTypes.JSON)
}
