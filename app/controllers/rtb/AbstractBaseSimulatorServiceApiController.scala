package controllers.rtb

import controllers.rtb.implementations.ServiceControllerPropertiesImplementation
import controllers.rtb.traits.ServiceControllerProperties
import javax.inject.Inject
import play.api.mvc._
import shared.com.ortb.enumeration.ControllerDefaultActionType
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config._
import shared.com.ortb.model.wrappers.http.ControllerGenericActionWrapper
import shared.com.ortb.persistent.Repositories
import shared.io.loggers.AppLogger

abstract class AbstractBaseSimulatorServiceApiController @Inject()(
  repositories : Repositories,
  appManager   : AppManager,
  components   : ControllerComponents)
  extends ServiceBaseApiController(repositories, appManager, components) {

  lazy val config : ConfigModel = appManager.config
  lazy val services : ServicesModel = config.server.services
  val currentServiceModel : ServiceModel
  lazy val selfProperties : ServiceControllerProperties = new ServiceControllerPropertiesImplementation(
    this,
    currentServiceModel)

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
    exception : Exception,
    controllerGenericActionWrapper : ControllerGenericActionWrapper
  ) : Result = {
    val message = controllerGenericActionWrapper.toString
    AppLogger.error(exception)
    AppLogger.error(message)
    BadRequest(message)
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
