package shared.com.ortb.demadSidePlatforms

import javax.inject.Inject
import play.api.mvc._
import shared.com.ortb.controllers.core.AbstractBaseSimulatorServiceApiController
import shared.com.ortb.demadSidePlatforms.traits.properties.DemandSidePlatformCorePropertiesContracts
import shared.com.ortb.enumeration.DemandSidePlatformBiddingAlgorithmType
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.auctionbid.biddingRequests.BidRequestModel
import shared.com.ortb.model.results.DemandSidePlatformBiddingRequestWrapperModel
import shared.io.extensions.TypeConvertExtensions._
import shared.io.loggers.AppLogger

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
        val bidRequestToString = bidRequest.toString
        val entityJson = demandSidePlatformJson.get
        val message = s"""
                         | BidRequest:$bidRequestToString
                         | BidRequestRow:${ bidRequestRow.toString }
                         | EntityJson:$entityJson
                         | DemandSidePlatformBidResponseModel:$maybeDemandSidePlatformBidResponseModel"""
        AppLogger.debug("BidProcessedData(Raw)", message)

        selfProperties
          .restWebApiOkJson
          .okHtmlWithStatus(maybeDemandSidePlatformBidResponseModel.)
      }
      else {
        return controller.noContent()
      }
    } catch {
      case e : Exception =>
        handleError(e)
    }
  }
}
