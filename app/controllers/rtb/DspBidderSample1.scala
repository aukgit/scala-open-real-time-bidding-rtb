package controllers.rtb

import shared.com.ortb.enumeration.DemandSidePlatformBiddingAlgorithmType.DemandSidePlatformBiddingAlgorithmType
import shared.com.ortb.importedModels.biddingRequests.ImpressionModel
import shared.com.ortb.model.results.DspBidderRequestModel
import shared.com.ortb.persistent.schema.Tables._
import shared.io.helpers.EmptyValidateHelper
import slick.jdbc.SQLiteProfile.api._

import scala.collection.mutable.ArrayBuffer

case class BidFailedReasonsModel(
    lostbids: Seq[LostbidRow]
)

case class ImpressionBiddableInfo(
  impression: ImpressionModel,
  isBiddable : Boolean,
  hasBanner: Boolean,
  advertises: Array[AdvertiseRow],
  advertisesFoundCount : Int
)

class DspBidderSample1(algorithmType: DemandSidePlatformBiddingAlgorithmType)
    extends DspBidder {
  val defaultIncrementNumber = 0.01
  val defaultStaticDeal = 0.03


  override def getBidPrices(
      request: DspBidderRequestModel): Option[DspBidderResultModel] = ???

  override def getBidStatic(
      request: DspBidderRequestModel): Option[DspBidderResultModel] = {
    val impressions: Seq[ImpressionModel] = request.bidRequest.imp.get
    val length = impressions.length
    val deals = new ArrayBuffer[ImpressionDealModel](length)
    val callStacks = new ArrayBuffer[CallStackModel](length)

    for (impression <- impressions) {
      if (impression.bidFloor.isDefined) {
        val deal: Double = impression.bidFloor.get + defaultIncrementNumber
        val impressionDealModel = ImpressionDealModel(impression, Some(deal))
        deals.addOne(impressionDealModel)

        val callStackModel = CallStackModel(
          deal = deal,
          performingAction =
            s"[getBidStatic] -> adding deals($deal) for given bid request.",
          isServedAnyDeal = true
        )

        callStacks.addOne(callStackModel)
      } else {
        deals.addOne(ImpressionDealModel(impression, Some(defaultStaticDeal)))

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
      DspBidderResultModel(request, request.bidRequest, Some(deals.toList))
    dspBidderResultModel.addCallStacks(callStacks)

    Some(dspBidderResultModel)
  }

  override def getBidStaticNoContent(
      request: DspBidderRequestModel): Option[DspBidderResultModel] = {
    val dspBidderResultModel =
      DspBidderResultModel(request, request.bidRequest, isNoContent = true)

    val callStackModel = CallStackModel(
      deal = defaultStaticDeal,
      performingAction = s"[getBidStaticNoContent] -> No deals.",
      isServedAnyDeal = true
    )

    dspBidderResultModel.addCallStack(callStackModel)

    Some(dspBidderResultModel)
  }

  def getLastFailedDeals(request: DspBidderRequestModel,
                         limit: Int = 5): BidFailedReasonsModel = {
    val controller = request.controller
    val demandSidePlatformId = controller.demandSideId
    val repositories = controller.repositories
    val lostBids = repositories.lostBids

    val lostBidsSqlProfileAction = lostBids
      .filter(r => r.demandsideplatformid === demandSidePlatformId)
      .sortBy(_.losingprice.desc)
      .sortBy(_.createddate.desc)
      .take(limit)
      .result

    val results = repositories.lostBidRepository
      .run(lostBidsSqlProfileAction)

    BidFailedReasonsModel(
      results
    )
  }

  def biddableImpressionInfo(request: DspBidderRequestModel): Array[ImpressionBiddableInfo] = {
    val controller = request.controller
    val demandSidePlatformId = controller.demandSideId
    val repositories = controller.repositories
    val advertises = repositories.advertises
    val impressions = request.bidRequest.imp.get

    impressions.map(impression => {
      if(EmptyValidateHelper.isDefined(impression.banner)){
        val banner = impression.banner

        advertises.filter(advertise =>
          advertise.isvideo === 0)
      }
    })


    throw new NotImplementedError()
  }

  override def getBidActual(
      request: DspBidderRequestModel): Option[DspBidderResultModel] = {
//    val isCurrentRequestBiddable = biddableImpressionInfo(
//      request: DspBidderRequestModel)
    throw new NotImplementedError()
  }

  override def getBidActualNoContent(
      request: DspBidderRequestModel): Option[DspBidderResultModel] = {
    val dspBidderResultModel =
      DspBidderResultModel(request, request.bidRequest, isNoContent = true)

    val callStackModel = CallStackModel(
      deal = defaultStaticDeal,
      performingAction = s"[getBidActualNoContent] -> No deals.",
      isServedAnyDeal = true
    )

    dspBidderResultModel.addCallStack(callStackModel)

    Some(dspBidderResultModel)
  }
}
