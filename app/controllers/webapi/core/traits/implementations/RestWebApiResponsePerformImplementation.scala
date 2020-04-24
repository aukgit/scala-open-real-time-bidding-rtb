package controllers.webapi.core.traits.implementations

import controllers.webapi.core.AbstractRestWebApi
import controllers.webapi.core.traits.RestWebApiResponsePerform
import play.api.mvc._
import play.mvc.Http.MimeTypes
import shared.com.ortb.model.wrappers.http._

trait RestWebApiResponsePerformImplementation[TTable, TRow, TKey]
  extends RestWebApiResponsePerform[TTable, TRow, TKey] {
  this : AbstractRestWebApi[TTable, TRow, TKey] =>

  def performBadRequestAsAction(
    httpFailedActionWrapper : Option[HttpFailedActionWrapper[TRow, TKey]]) : Action[AnyContent] = {
    if (httpFailedActionWrapper.isDefined) {
      components.actionBuilder(BadRequest(httpFailedActionWrapper.toString))
    } else {
      components.actionBuilder(BadRequest(getDefaultFailedMessage()))
    }
  }

  def performBadResponseOnException(
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
