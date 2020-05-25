package shared.com.ortb.demadSidePlatforms

import shared.com.ortb.controllers.core.AbstractBaseSimulatorServiceApiController
import io.circe.generic.auto._
import javax.inject.Inject
import play.api.mvc._
import shared.com.ortb.demadSidePlatforms.traits.properties.DemandSidePlatformCorePropertiesContracts
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.auctionbid.biddingRequests.BidRequestModel
import shared.com.ortb.persistent.Repositories
import shared.io.helpers.JsonHelper

class DemandSidePlatformSimulatorServiceApiController @Inject()(
  repositories : Repositories,
  appManager : AppManager,
  components : ControllerComponents)
  extends AbstractBaseSimulatorServiceApiController(
    repositories,
    appManager,
    components) with DemandSidePlatformCorePropertiesContracts {

  lazy val demandSideId = 1

  def makeBidRequest : Action[AnyContent] = Action { implicit request =>
    try {
      val bodyRaw = request.body.asText.get
      logger.debug(bodyRaw)

      val bidRequest = JsonHelper.toObjectUsingParser[BidRequestModel](bodyRaw)
      val bidRequestToString = bidRequest.get.toString
      val entityJson = demandSidePlatformJson.get
      val response = s"$bidRequestToString \n $entityJson"
      selfProperties.restWebApiOkJson.OkJson(response)
    } catch {
      case e : Exception =>
        handleError(e)
    }
  }

  override val coreProperties : DemandSidePlatformCorePropertiesContracts = this
}
