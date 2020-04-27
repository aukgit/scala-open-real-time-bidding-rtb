package shared.com.ortb.demadSidePlatforms

import controllers.rtb.core.AbstractBaseSimulatorServiceApiController
import io.circe.generic.auto._
import javax.inject.Inject
import play.api.mvc._
import shared.com.ortb.demadSidePlatforms.traits.properties.DemandSidePlatformCoreProperties
import shared.com.ortb.importedModels.biddingRequests.BidRequest
import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.Repositories
import shared.io.helpers.JsonHelper

class DemandSidePlatformSimulatorServiceApiController @Inject()(
  repositories : Repositories,
  appManager : AppManager,
  components : ControllerComponents)
  extends AbstractBaseSimulatorServiceApiController(
    repositories,
    appManager,
    components) with DemandSidePlatformCoreProperties {

  lazy override val demandSideId = 1

  def makeBidRequest : Action[AnyContent] = Action { implicit request =>
    try {
      val bodyRaw = request.body.asText.get
      logger.debug(bodyRaw)

      val bidRequest = JsonHelper.toObjectUsingParser[BidRequest](bodyRaw)
      val bidRequestToString = bidRequest.get.toString
      val entityJson = demandSidePlatformJson.get
      val response = s"$bidRequestToString \n $entityJson"
      selfProperties.restWebApiOkJson.OkJson(response)
    } catch {
      case e : Exception =>
        handleError(e)
    }
  }
}
