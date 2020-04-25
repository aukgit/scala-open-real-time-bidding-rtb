package controllers.rtb

import controllers.rtb.core.AbstractBaseSimulatorServiceApiController
import io.circe.generic.semiauto._
import io.circe._
import io.circe.generic.auto._
import javax.inject.Inject
import play.api.mvc._
import shared.com.ortb.importedModels.biddingRequests.BidRequest
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config.ServiceModel
import shared.com.ortb.persistent.Repositories
import shared.io.helpers.JsonHelper

class DemandSidePlatformSimulatorServiceApiController @Inject()(
  repositories : Repositories,
  appManager      : AppManager,
  components : ControllerComponents)
  extends AbstractBaseSimulatorServiceApiController(repositories, appManager, components) {
  override val currentServiceModel : ServiceModel = services.demandSidePlatForms(0)

  def makeBidRequest : Action[AnyContent] = Action { implicit request =>
    try {
      val bodyRaw = request.body.asText.get
      val decoder = deriveDecoder[BidRequest]
      logger.debug(bodyRaw)
      val bidRequest = JsonHelper.toObjectUsingParser[BidRequest](bodyRaw)(decoder)
      selfProperties.restWebApiOkJson.OkJson(bidRequest.get.toString)
    } catch {
      case e : Exception =>
        handleError(e)
    }
  }

}
