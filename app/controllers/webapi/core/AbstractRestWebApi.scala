package controllers.webapi.core

import controllers.webapi.core.traits.{ RestWebApiContracts, RestWebApiResponsePerform }
import controllers.webapi.core.traits.implementations.{ RestWebApiBodyProcessorImplementation, RestWebApiHandleErrorImplementation }
import play.api.mvc._
import shared.com.ortb.model.wrappers.http._
import shared.io.loggers.AppLogger

abstract class AbstractRestWebApi[TTable, TRow, TKey](
  val components : ControllerComponents)
  extends AbstractController(components)
    with RestWebApiContracts[TTable, TRow, TKey]
    with RestWebApiHandleErrorImplementation[TTable, TRow, TKey]
    with RestWebApiBodyProcessorImplementation[TTable, TRow, TKey]
    with RestWebApiResponsePerform[TTable, TRow, TKey] {

  def getRequestUri(request : Request[AnyContent]) : String = {
    request.uri.toString
  }

  def createHttpFailedActionWrapper(
    message : String,
    actionWrapper : ControllerGenericActionWrapper,
    methodName : String = ""
  ) : HttpFailedActionWrapper[TRow, TKey] = {
    AppLogger.error(s"${ message } $methodName")
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


}
