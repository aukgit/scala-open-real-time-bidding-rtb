package controllers.apis

import io.circe.generic.auto._
import io.circe.syntax._
import javax.inject.Inject
import play.api.mvc._
import services.CampaignService
import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.Repositories
import shared.io.logger.AppLogger

class CampaignsApiController @Inject()(
  appManager: AppManager,
  campaignService : CampaignService,
  components : ControllerComponents)
  extends AbstractController(components) {
  def campaigns : Action[AnyContent] = Action { implicit request =>
    val campaigns = campaignService.getAll
    val json = campaigns.asJson.spaces2
    // println(json)
    Ok(json)
  }
}
