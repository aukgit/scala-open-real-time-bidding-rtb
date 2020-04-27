package shared.com.ortb.demadSidePlatforms

import shared.com.ortb.demadSidePlatforms.traits.logics.DemandSidePlatformStaticBidResponseLogic
import shared.com.ortb.demadSidePlatforms.traits.properties.{ DemandSidePlatformBiddingProperties, DemandSidePlatformCoreProperties }
import shared.com.ortb.importedModels.biddingRequests.ImpressionModel
import shared.com.ortb.model
import shared.com.ortb.model._
import shared.com.ortb.model.config.DemandSidePlatformConfigurationModel
import shared.com.ortb.model.results.DspBidderRequestModel

import scala.collection.mutable.ArrayBuffer

class DemandSidePlatformStaticBidResponseLogicImplementation
(demandSidePlatformCoreProperties : DemandSidePlatformCoreProperties)
  extends DemandSidePlatformStaticBidResponseLogic with DemandSidePlatformBiddingProperties {

  lazy override val demandSidePlatformConfiguration : DemandSidePlatformConfigurationModel =
    demandSidePlatformCoreProperties.demandSidePlatformConfiguration

  override def getBidStatic(
    request : DspBidderRequestModel) : Option[DemandSidePlatformBidResponseModel] = {
    val impressions : Seq[ImpressionModel] = request.bidRequest.imp.get
    val length = impressions.length
    val deals = new ArrayBuffer[ImpressionDealModel](length)
    val callStacks = new ArrayBuffer[CallStackModel](length)

    for (impression <- impressions) {
      if (impression.bidFloor.isDefined) {
        val deal : Double = impression.bidFloor.get + defaultIncrementNumber
        val impressionDealModel = model.ImpressionDealModel(impression, Some(deal))
        deals.addOne(impressionDealModel)

        val callStackModel = CallStackModel(
          deal = deal,
          performingAction =
            s"[getBidStatic] -> adding deals($deal) for given bid request.",
          isServedAnyDeal = true
        )

        callStacks.addOne(callStackModel)
      } else {
        deals.addOne(model.ImpressionDealModel(impression, Some(defaultStaticDeal)))

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
      model.DemandSidePlatformBidResponseModel(request, request.bidRequest, Some(deals.toList))
    dspBidderResultModel.addCallStacks(callStacks)

    Some(dspBidderResultModel)
  }

  override def getBidStaticNoContent(
    request : DspBidderRequestModel) : Option[DemandSidePlatformBidResponseModel] = {
    val dspBidderResultModel =
      model.DemandSidePlatformBidResponseModel(request, request.bidRequest, isNoContent = true)

    val callStackModel = CallStackModel(
      deal = demandSidePlatformCoreProperties.noDealPrice,
      performingAction = s"[getBidStaticNoContent] -> No deals.",
      isServedAnyDeal = false
    )

    dspBidderResultModel.addCallStack(callStackModel)

    Some(dspBidderResultModel)
  }
}
