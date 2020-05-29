package shared.com.ortb.controllers.core

import javax.inject.Inject
import play.api.mvc._
import shared.com.ortb.controllers.implementations.{ ServiceControllerPropertiesContractsImplementation, WebApiResponseImplementation }
import shared.com.ortb.controllers.traits.properties.ServiceControllerCorePropertiesContracts
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config._
import shared.com.ortb.model.wrappers.http.ControllerGenericActionWrapper
import shared.io.loggers.AppLogger

abstract class AbstractBaseSimulatorServiceApiController @Inject()(
  appManager : AppManager,
  components : ControllerComponents)
  extends ServiceCoreApiController(appManager, components)
    with ServiceControllerCorePropertiesContracts {
  lazy override val webApiResponse : WebApiResponseImplementation = serviceControllerProperties.webApiResponse
  lazy override val serviceTitle : String = currentServiceModel.title
  lazy val config : ConfigModel = appManager.config
  lazy val services : ServicesModel = config.server.services
  lazy val serviceControllerProperties : ServiceControllerCorePropertiesContracts =
    new ServiceControllerPropertiesContractsImplementation(
      this,
      components,
      currentServiceModel)

  def getServiceName : Action[AnyContent] = Action { implicit request =>
    try {
      serviceControllerProperties
        .webApiResponse
        .okJson(serviceControllerProperties.serviceTitle)
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
