package controllers.rtb

import controllers.rtb.implementations.ServiceControllerPropertiesImplementation
import javax.inject.Inject
import play.api.mvc._
import shared.com.ortb.enumeration.ControllerDefaultActionType
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.wrappers.http._
import shared.com.ortb.persistent.Repositories
import shared.io.helpers.{ FileHelper, JsonHelper }
import shared.io.loggers.AppLogger

class BidRequestSimulatorServiceApiController @Inject()(
  repositories : Repositories,
  appManager      : AppManager,
  components      : ControllerComponents)
  extends ServiceBaseApiController(repositories, appManager, components) {

  lazy val selfProperties = new ServiceControllerPropertiesImplementation(this)
  lazy val jsonDirectory = "jsonRequestSamples"

  def getServiceName : Action[AnyContent] = Action { implicit request =>
    val actionWrapper = ControllerGenericActionWrapper(
      ControllerDefaultActionType.GetOrRead,
      Some(request))

    try {
      selfProperties.restWebApiOkJson.OkJson(selfProperties.serviceTitle)
    } catch {
      case e : Exception =>
        handleError(e)
    }
  }

  def handleError(
    exception : Exception
  ) : Result = {
    AppLogger.error(exception)
    BadRequest(exception.toString)
  }

  def handleError(
    exception                      : Exception,
    controllerGenericActionWrapper : ControllerGenericActionWrapper
  ) : Result = {
    val message = controllerGenericActionWrapper.toString
    AppLogger.error(exception)
    AppLogger.error(message)
    BadRequest(message)
  }

  def getAvailableCommands : Action[AnyContent] = Action { implicit request =>
    //    val actionWrapper = ControllerGenericActionWrapper(
    //      ControllerDefaultActionType.GetOrRead,
    //      Some(request))

    try {
      val jsonString = JsonHelper.toJson(selfProperties.serviceModel.routing).get.toString()
      selfProperties.restWebApiOkJson.OkJson(jsonString)
    } catch {
      case e : Exception =>
        AppLogger.error(e)
        BadRequest
    }
  }

  def getBannerRequestSample(bannerSuffix : String) : Action[AnyContent] = Action { implicit request =>
    try {
      val jsonString = FileHelper.getContentsFromResourcesPaths(
        jsonDirectory,
        "requests",
        s"${ bannerSuffix }-bid-request.json")

      selfProperties.restWebApiOkJson.OkJson(jsonString)
    } catch {
      case e : Exception =>
        AppLogger.error(e)
        BadRequest
    }
  }

  def performBadResponse(
    controllerGenericActionWrapper : Option[ControllerGenericActionWrapper]) : Result = {
    if (controllerGenericActionWrapper.isDefined) {
      return BadRequest(controllerGenericActionWrapper.toString)
    }

    BadRequest(getDefaultFailedMessage())
  }

  def performBadResponseOnException(
    controllerGenericActionWrapper : Option[ControllerGenericActionWrapper]) : Result =
    BadRequest(controllerGenericActionWrapper.toString)

  def performOkayOnEntity(
    controllerGenericActionWrapper : Option[ControllerGenericActionWrapper]) : Result = {
    if (controllerGenericActionWrapper.isDefined) {
      return Ok(controllerGenericActionWrapper.toString)
    }

    Ok(getDefaultSuccessMessage())
  }

  def performOkay(
    controllerGenericActionWrapper : Option[ControllerGenericActionWrapper]) : Result = {
    if (controllerGenericActionWrapper.isDefined) {
      return Ok(controllerGenericActionWrapper.toString)
    }

    Ok(getDefaultSuccessMessage())
  }

  def performBadResponseAsAction(
    controllerGenericActionWrapper : Option[ControllerGenericActionWrapper]) : Action[AnyContent] = {
    if (controllerGenericActionWrapper.isDefined) {
      components.actionBuilder(BadRequest(controllerGenericActionWrapper.toString))
    } else {
      components.actionBuilder(BadRequest(getDefaultFailedMessage()))
    }
  }
}
