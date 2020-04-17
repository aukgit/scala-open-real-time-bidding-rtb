package controllers.apis

import io.circe.generic.auto._
import io.circe.syntax._
import javax.inject.Inject
import play.api.mvc._
import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.Repositories
import shared.io.logger.AppLogger

class CampaignsApiController @Inject()(
  appManager : AppManager,
  components : ControllerComponents)
  extends AbstractController(components) {
  def campaigns : Action[AnyContent] = Action { implicit request =>
    val repositories = new Repositories(appManager)
    val campaigns = repositories.campaignRepository.getAll.toArray
    AppLogger.logEntitiesNonFuture(isExecute = true, campaigns)
    val json = campaigns.asJson.spaces2
    println(json)
    Ok(json)
  }
}
