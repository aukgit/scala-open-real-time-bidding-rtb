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
import shared.io.loggers.AppLogger

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

      if (maybeDemandSidePlatformBidResponseModel.isDefined) {
        val dspBidResponseModel = maybeDemandSidePlatformBidResponseModel.get
        val bidRequestToString = bidRequest.toString
        val entityJson = demandSidePlatformJson.get
        val message = s"""
                         | $bidRequestToString
                         | ${ bidRequestRow.toString }
                         | EntityJson:$entityJson
                         | DemandSidePlatformBidResponseModel:$dspBidResponseModel""".stripMargin
        AppLogger.debug("BidProcessedData(Raw)", message)

        val bidResponseJsonTry = Try(dspBidResponseModel
          .bidResponseWrapper
          .bidResponse
          .get
          .toJsonString)

        if (bidResponseJsonTry.isSuccess) {
          selfProperties
            .restWebApiOkJson
            .okJsonWithStatus(bidResponseJsonTry.get)
        }
        else {
          val noBid = AppConstants.biddingConstants.emptyStaticBidResponse

          selfProperties
            .restWebApiOkJson
            .okJsonWithStatus(noBid)
        }
      }
      else {
        selfProperties
          .restWebApiOkJson
          .noContent
      }

    } catch {
      case e : Exception =>
        handleError(e)
    }
  }
}
