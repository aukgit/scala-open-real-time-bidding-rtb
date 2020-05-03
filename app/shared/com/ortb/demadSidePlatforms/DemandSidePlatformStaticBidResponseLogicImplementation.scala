package shared.com.ortb.demadSidePlatforms

import shared.com.ortb.demadSidePlatforms.traits.logics.DemandSidePlatformStaticBidResponseLogic
import shared.com.ortb.demadSidePlatforms.traits.properties.{ DemandSidePlatformBiddingProperties, DemandSidePlatformCorePropertiesContracts }
import shared.com.ortb.model.auctionbid.biddingRequests.ImpressionModel
import shared.com.ortb.model
import shared.com.ortb.model.{ auctionbid, _ }
import shared.com.ortb.model.auctionbid.{ DemandSidePlatformBidResponseModel, ImpressionDealModel }
import shared.com.ortb.model.auctionbid.bidresponses.BidResponseModelWrapper
import shared.com.ortb.model.config.DemandSidePlatformConfigurationModel
import shared.com.ortb.model.logging.CallStackModel
import shared.com.ortb.model.results.DemandSidePlatformBiddingRequestWrapperModel

import scala.collection.mutable.ArrayBuffer

class DemandSidePlatformStaticBidResponseLogicImplementation
(demandSidePlatformCoreProperties : DemandSidePlatformCorePropertiesContracts)
  extends DemandSidePlatformStaticBidResponseLogic with DemandSidePlatformBiddingProperties {

  lazy override val demandSidePlatformConfiguration : DemandSidePlatformConfigurationModel =
    demandSidePlatformCoreProperties.demandSidePlatformConfiguration

  override def getBidStatic(
    request : DemandSidePlatformBiddingRequestWrapperModel) : Option[DemandSidePlatformBidResponseModel] = {
    val impressions : Seq[ImpressionModel] = request.bidRequestModel.imp.get
    val length = impressions.length
    val deals = new ArrayBuffer[ImpressionDealModel](length)
    val callStacks = new ArrayBuffer[CallStackModel](length)

    for (impression <- impressions) {
      if (impression.bidfloor.isDefined) {
        val deal : Double = impression.bidfloor.get + defaultIncrementNumber
        val impressionDealModel = ImpressionDealModel(impression, deal)
        deals.addOne(impressionDealModel)

        val callStackModel = CallStackModel(
          deal = deal,
          performingAction =
            s"[getBidStatic] -> adding deals($deal) for given bid request.",
          isServedAnyDeal = true
        )

        callStacks.addOne(callStackModel)
      } else {
        deals.addOne(auctionbid.ImpressionDealModel(impression, defaultStaticDeal))

        val callStackModel = CallStackModel(
          deal = defaultStaticDeal,
          performingAction =
            s"[getBidStatic] -> adding deals($defaultStaticDeal) for given bid request.",
          isServedAnyDeal = true
        )

        callStacks.addOne(callStackModel)
      }
    }

    val dspBidderResultModel =
      DemandSidePlatformBidResponseModel(
        request,
        request.bidRequestModel,
        bidResponseWrapper = null,
        Some(deals.toList))
    dspBidderResultModel.addCallStacks(callStacks)

    Some(dspBidderResultModel)
  }

  override def getBidStaticNoContent(
    request : DemandSidePlatformBiddingRequestWrapperModel) : Option[DemandSidePlatformBidResponseModel] = {
//    val dspBidderResultModel =
//      model.DemandSidePlatformBidResponseModel(
//        request,
//        request.bidRequest,
//        isNoContent = true,
//        BidResponseModelWrapper = null)
//
//    val callStackModel = CallStackModel(
//      deal = demandSidePlatformCoreProperties.noDealPrice,
//      performingAction = s"[getBidStaticNoContent] -> No deals.",
//      isServedAnyDeal = false
//    )
//
//    dspBidderResultModel.addCallStack(callStackModel)
//
//    Some(dspBidderResultModel)
    throw new NotImplementedError()

  }
}
