package controllers.rtb

import controllers.rtb.core.AbstractBaseSimulatorServiceApiController
import io.circe.generic.auto._
import javax.inject.Inject
import play.api.mvc._
import shared.com.ortb.enumeration.DemandSidePlatformBiddingAlgorithmType.DemandSidePlatformBiddingAlgorithmType
import shared.com.ortb.importedModels.biddingRequests.BidRequest
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config.ServiceModel
import shared.com.ortb.persistent.Repositories
import shared.com.ortb.persistent.repositories.DemandSidePlatformRepository
import shared.com.ortb.persistent.schema.Tables
import shared.io.helpers.JsonHelper

class DemandSidePlatformSimulatorServiceApiController @Inject()(
  repositories    : Repositories,
  appManager      : AppManager,
  components      : ControllerComponents)
  extends AbstractBaseSimulatorServiceApiController(
    repositories,
    appManager,
    components) {
  lazy val demandSideId = 1
  override val currentServiceModel : ServiceModel =
    services.demandSidePlatForms(demandSideId - 1)
  lazy val demandSidePlatformRepository : DemandSidePlatformRepository =
    repositories.demandSidePlatformRepository
  lazy val demandSidePlatformEntity : Option[Tables.DemandsideplatformRow] =
    demandSidePlatformRepository
      .getById(demandSideId)
  lazy val demandSidePlatformJson : Option[String] = demandSidePlatformRepository
    .fromEntityToJson(demandSidePlatformEntity)

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
