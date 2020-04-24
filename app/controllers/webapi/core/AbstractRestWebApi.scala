package controllers.webapi.core

import controllers.webapi.core.traits.{ RestWebApiContracts, RestWebApiContractsImplementation, RestWebApiHandleErrorImplementation }
import play.api.libs.json.Writes
import play.api.mvc.{ Action, _ }
import play.libs.Json
import play.mvc.Http.MimeTypes
import shared.com.ortb.enumeration._
import shared.com.ortb.model.requests
import shared.com.ortb.model.requests.HttpSuccessResponseCreateRequestModel
import shared.com.ortb.model.wrappers.http._
import shared.com.ortb.model.wrappers.persistent.{ WebApiEntitiesResponseWrapper, WebApiEntityResponseWrapper }
import shared.com.ortb.webapi.traits.{ RestWebApiDeleteAction, RestWebApiGetAllAction }
import shared.io.helpers._
import shared.io.loggers.AppLogger

abstract class AbstractRestWebApi[TTable, TRow, TKey](
  val components : ControllerComponents)
  extends AbstractController(components)
    with RestWebApiContracts[TTable, TRow, TKey]
    with RestWebApiContractsImplementation[TTable, TRow, TKey]
    with RestWebApiHandleErrorImplementation {

  def getRequestUri(request : Request[AnyContent]) : String = {
    request.uri.toString
  }

  def createHttpFailedActionWrapper(
    message: String,
    actionWrapper: ControllerGenericActionWrapper,
    methodName : String = ""
  ) : HttpFailedActionWrapper[TRow, TKey] = {
    AppLogger.error(s"${message} $methodName")
    val httpFailedActionWrapper = HttpFailedActionWrapper[TRow, TKey](
      additionalMessage = Some(message),
      methodName = Some(methodName),
      controllerGenericActionWrapper = actionWrapper)

    httpFailedActionWrapper
  }

  override def getDefaultFailedMessage(
    controllerGenericActionWrapper : Option[ControllerGenericActionWrapper] = None,
    entity : Option[TRow] = None,
    message : String = "") : String = "Failed Operation" // TODO Enhance

  override def getDefaultSuccessMessage(
    controllerGenericActionWrapper : Option[ControllerGenericActionWrapper] = None,
    entity : Option[TRow] = None,
    message : String = "") : String = "Success Operation" // TODO Enhance

  override def performBadRequest(
    httpFailedActionWrapper : Option[HttpFailedActionWrapper[TRow, TKey]]) : Result = {
    if (httpFailedActionWrapper.isDefined) {
      return BadRequest(httpFailedActionWrapper.toString)
    }

    BadRequest(getDefaultFailedMessage())
  }

}
