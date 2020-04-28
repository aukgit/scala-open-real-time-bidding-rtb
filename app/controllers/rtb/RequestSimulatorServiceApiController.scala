package controllers.rtb

import controllers.rtb.core.AbstractBaseSimulatorServiceApiController
import javax.inject.Inject
import play.api.mvc._
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config._
import shared.com.ortb.persistent.Repositories
import shared.io.helpers.{ FileHelper, JsonHelper }
import shared.io.loggers._

class RequestSimulatorServiceApiController @Inject()(
  repositories : Repositories,
  appManager : AppManager,
  components : ControllerComponents)
  extends AbstractBaseSimulatorServiceApiController(repositories, appManager, components) {

  lazy val currentServiceModel : ServiceModel = services.requestSimulatorService

  lazy val jsonDirectory = "jsonRequestSamples"

  def getAvailableCommands : Action[AnyContent] = Action { implicit request =>
    try {
      val jsonString = JsonHelper.toJson(selfProperties.currentServiceModel.routing).get.toString()
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

  lazy override val databaseLogger : DatabaseLogTracer = new DatabaseLogTracerImplementation(appManager)
}
