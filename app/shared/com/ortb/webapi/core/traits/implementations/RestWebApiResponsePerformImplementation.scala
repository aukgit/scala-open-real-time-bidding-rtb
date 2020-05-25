package shared.com.ortb.webapi.core.traits.implementations

import shared.com.ortb.webapi.core.AbstractRestWebApi
import shared.com.ortb.webapi.core.traits.RestWebApiResponsePerform
import play.api.mvc._
import play.mvc.Http.MimeTypes
import shared.com.ortb.model.wrappers.http._

trait RestWebApiResponsePerformImplementation[TTable, TRow, TKey]
  extends RestWebApiResponsePerform[TTable, TRow, TKey] {
  this : AbstractRestWebApi[TTable, TRow, TKey] =>

  override def performBadResponse(
    httpFailedActionWrapper : Option[HttpFailedActionWrapper[TRow, TKey]]) : Result = {
    if (httpFailedActionWrapper.isDefined) {
      return BadRequest(httpFailedActionWrapper.toString)
    }

    BadRequest(getDefaultFailedMessage())
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

  def performBadResponseAsAction(
    httpFailedActionWrapper : Option[HttpFailedActionWrapper[TRow, TKey]]) : Action[AnyContent] = {
    if (httpFailedActionWrapper.isDefined) {
      components.actionBuilder(BadRequest(httpFailedActionWrapper.toString))
    } else {
      components.actionBuilder(BadRequest(getDefaultFailedMessage()))
    }
  }

  override def OkJson(jsonString : String): Result = Ok(jsonString).as(MimeTypes.JSON)
}
