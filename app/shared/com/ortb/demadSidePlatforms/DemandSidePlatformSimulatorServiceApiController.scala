package shared.com.ortb.demadSidePlatforms

import io.circe.generic.auto._
import javax.inject.{ Inject, Singleton }
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

@Singleton
class DemandSidePlatformSimulatorServiceApiController @Inject()(
  appManager : AppManager,
  components : ControllerComponents)
  extends AbstractBaseSimulatorServiceApiController(appManager, components)
    with DemandSidePlatformCorePropertiesContracts {

  lazy val demandSideId = 1
  lazy override val coreProperties : DemandSidePlatformCorePropertiesContracts =
    this
  lazy val agent = new DemandSidePlatformBiddingAgent(
    coreProperties,
    demandSideId,
    DemandSidePlatformBiddingAlgorithmType.LinearBid)

  def makeBidRequest : Action[AnyContent] = Action { implicit request =>
    try {
      val bodyRaw = request.body.asText.get
      log(bodyRaw)
      var isFailed = false
      val bidRequest = bodyRaw.asFromJson[BidRequestModel]
      val bidRequestRow = agent.getBidRequestToBidRequestRow(bidRequest)
      val requestWrapperModel = DemandSidePlatformBiddingRequestWrapperModel(
        bidRequest,
        bidRequestRow,
        demandSideId
      )

      val maybeDemandSidePlatformBidResponseModel =
        agent.getBid(requestWrapperModel)
      maybeDemandSidePlatformBidResponseModel
        .isEmpty
        .dosOnTrue(() => isFailed = true)

      val dspBidResponseModel = maybeDemandSidePlatformBidResponseModel.get
      val bidResponseJsonTry =
        Try({
          throw new Exception("Hello")
          dspBidResponseModel
            .bidResponseWrapper
            .bidResponse
            .get
            .toJsonString
        })

      bidResponseJsonTry
        .isFailure
        .dosOnTrue(() => isFailed = true)

      if (isFailed) {
        noBidResponse
      }
      else {
        serviceControllerProperties
          .webApiResponse
          .okJsonWithHeader(
            bidResponseJsonTry.get,
            defaultOkResponseHeader)
      }
    } catch {
      case e : Exception =>
        handleError(e)
    }
  }

  private def noBidResponse : Result = {
    val noBid = AppConstants.BiddingConstants.emptyStaticBidResponse

    serviceControllerProperties.webApiResponse
      .okJsonWithHeader(noBid, defaultOkResponseHeader)
  }
}
