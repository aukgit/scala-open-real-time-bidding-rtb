package controllers.rtb

import javax.inject.Inject
import play.api.mvc._
import shared.com.ortb.controllers.core.AbstractBaseSimulatorServiceApiController
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config._
import shared.io.helpers.{ FileHelper, JsonHelper }
import shared.io.loggers._

class RequestSimulatorServiceApiController @Inject()(
  appManager : AppManager,
  components : ControllerComponents)
  extends AbstractBaseSimulatorServiceApiController(appManager, components) {

  lazy val currentServiceModel : ServiceModel = services.requestSimulatorService
  lazy val jsonDirectory = "jsonRequestSamples"

  def getAvailableCommands : Action[AnyContent] = Action { implicit request =>
    try {
      val routing = selfProperties
        .currentServiceModel
        .routing

      val jsonString = JsonHelper
        .toJson(routing)
        .get
        .toString()

      return selfProperties
        .webApiResponse
        .okJson(jsonString)
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

      return selfProperties
        .webApiResponse
        .okJson(jsonString)
    } catch {
      case e : Exception =>
        AppLogger.error(e)
        BadRequest
    }
  }

  lazy override val databaseLogger : DatabaseLogTracer = new DatabaseLogTracerImplementation(
    appManager,
    this.getClass.getName)
}
