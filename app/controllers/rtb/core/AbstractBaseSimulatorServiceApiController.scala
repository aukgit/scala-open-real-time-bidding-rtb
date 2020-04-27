package controllers.rtb.core

import controllers.rtb.implementations.{ RestWebApiOkJsonImplementation, ServiceControllerPropertiesImplementation }
import controllers.rtb.traits.properties.ServiceControllerCoreProperties
import javax.inject.Inject
import play.api.Logger
import play.api.mvc._
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config._
import shared.com.ortb.model.wrappers.http.ControllerGenericActionWrapper
import shared.com.ortb.persistent.Repositories
import shared.io.loggers.AppLogger

abstract class AbstractBaseSimulatorServiceApiController @Inject()(
  repositories : Repositories,
  appManager   : AppManager,
  components   : ControllerComponents)
  extends ServiceBaseApiController(repositories, appManager, components)
    with ServiceControllerCoreProperties {

  lazy override val restWebApiOkJson : RestWebApiOkJsonImplementation = selfProperties.restWebApiOkJson
  lazy override val serviceTitle : String = currentServiceModel.title
  lazy val config : ConfigModel = appManager.config
  lazy val services : ServicesModel = config.server.services
  lazy val selfProperties : ServiceControllerCoreProperties = new ServiceControllerPropertiesImplementation(
    this,
    currentServiceModel)
  val currentServiceModel : ServiceModel

  def getServiceName : Action[AnyContent] = Action { implicit request =>
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
