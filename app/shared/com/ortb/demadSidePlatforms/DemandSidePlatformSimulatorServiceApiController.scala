package shared.com.ortb.demadSidePlatforms

import io.circe.generic.auto._
import javax.inject.Inject
import play.api.mvc._
import shared.com.ortb.constants.AppConstants
import shared.com.ortb.controllers.core.AbstractBaseSimulatorServiceApiController
import shared.com.ortb.demadSidePlatforms.traits.properties.DemandSidePlatformCorePropertiesContracts
import shared.com.ortb.enumeration.DemandSidePlatformBiddingAlgorithmType
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.auctionbid.biddingRequests.BidRequestModel
import shared.com.ortb.model.results.DemandSidePlatformBiddingRequestWrapperModel
import shared.io.extensions.TypeConvertExtensions._

import scala.util.Try

class DemandSidePlatformSimulatorServiceApiController @Inject()(
  appManager : AppManager,
  components : ControllerComponents)
  extends AbstractBaseSimulatorServiceApiController(
    appManager,
    components) with DemandSidePlatformCorePropertiesContracts {

  lazy val demandSideId = 1
  lazy override val coreProperties : DemandSidePlatformCorePropertiesContracts = this
  lazy val agent = new DemandSidePlatformBiddingAgent(
    coreProperties,
    demandSideId,
    DemandSidePlatformBiddingAlgorithmType.LinearBid)

  def makeBidRequest : Action[AnyContent] = Action { implicit request =>
    try {
      val bodyRaw = request.body.asText.get
      logger.debug(bodyRaw)
      val bidRequest = bodyRaw.asFromJson[BidRequestModel]
      val bidRequestRow = agent.getBidRequestToBidRequestRow(bidRequest)
      val requestWrapperModel = DemandSidePlatformBiddingRequestWrapperModel(
        bidRequest,
        bidRequestRow,
        demandSideId
      )

      val maybeDemandSidePlatformBidResponseModel = agent.getBid(requestWrapperModel)
      if (maybeDemandSidePlatformBidResponseModel.isEmpty) {
        noBidResponse
        return
      }

      val dspBidResponseModel = maybeDemandSidePlatformBidResponseModel.get
      val bidResponseJsonTry = Try(dspBidResponseModel
        .bidResponseWrapper
        .bidResponse
        .get
        .toJsonString)

      if (bidResponseJsonTry.isFailure) {
        noBidResponse
        return
      }

      selfProperties
        .webApiResult
        .okJsonWithHeader(
          bidResponseJsonTry.get,
          defaultOkResponseHeader)
    } catch {
      case e : Exception =>
        handleError(e)
    }
  }

  private def noBidResponse = {
    val noBid = AppConstants
      .BiddingConstants
      .emptyStaticBidResponse

    selfProperties
      .webApiResult
      .okJsonWithHeader(
        noBid,
        defaultNoResponseHeader)
  }
}
