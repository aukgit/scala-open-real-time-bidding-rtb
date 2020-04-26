package controllers.rtb

import shared.com.ortb.enumeration.DemandSidePlatformBiddingAlgorithmType.DemandSidePlatformBiddingAlgorithmType
import shared.com.ortb.importedModels.biddingRequests.Impression
import shared.com.ortb.model.results.DspBidderRequestModel
import shared.com.ortb.persistent.schema.Tables._
import shared.com.ortb.persistent.schema.Tables
import shared.com.ortb.persistent.schema.Tables._
import shared.com.repository.RepositoryBase
import slick.jdbc.SQLiteProfile.api._
import scala.collection.mutable.ArrayBuffer
import slick.dbio.Effect
import slick.jdbc.SQLiteProfile
import slick.lifted.Query
import slick.sql.FixedSqlAction

case class BidFailedReasonsModel(
  bidRequests: Seq[BidrequestRow],
  bidResponses: Seq[BidresponseRow]
)

class DspBidderSample1(algorithmType : DemandSidePlatformBiddingAlgorithmType)
  extends DspBidder {
  val defaultIncrementNumber = 0.01
  val defaultStaticDeal = 0.03

  override def getBidPrices(
    request : DspBidderRequestModel) : Option[DspBidderResultModel] = ???

  override def getBidStatic(
    request : DspBidderRequestModel) : Option[DspBidderResultModel] = {
    val impressions : Seq[Impression] = request.bidRequest.imp.get
    val length = impressions.length
    val deals = new ArrayBuffer[ImpressionDealModel](length)
    val callStacks = new ArrayBuffer[CallStackModel](length)

    for (impression <- impressions) {
      if (impression.bidFloor.isDefined) {
        val deal : Double = impression.bidFloor.get + defaultIncrementNumber
        val impressionDealModel = ImpressionDealModel(impression, Some(deal))
        deals.addOne(impressionDealModel)

        val callStackModel = CallStackModel(
          deal = deal,
          performingAction = s"[getBidStatic] -> adding deals($deal) for given bid request.",
          isServedAnyDeal = true
        )

        callStacks.addOne(callStackModel)
      }
      else {
        deals.addOne(ImpressionDealModel(impression, Some(defaultStaticDeal)))

        val callStackModel = CallStackModel(
          deal = defaultStaticDeal,
          performingAction = s"[getBidStatic] -> adding deals($defaultStaticDeal) for given bid request.",
          isServedAnyDeal = true
        )

        callStacks.addOne(callStackModel)
      }
    }

    val dspBidderResultModel = DspBidderResultModel(request, request.bidRequest, Some(deals.toList))
    dspBidderResultModel.addCallStacks(callStacks)

    Some(dspBidderResultModel)
  }

  override def getBidStaticNoContent(
    request : DspBidderRequestModel) : Option[DspBidderResultModel] = {
    val dspBidderResultModel = DspBidderResultModel(
      request,
      request.bidRequest,
      isNoContent = true)

    val callStackModel = CallStackModel(
      deal = defaultStaticDeal,
      performingAction = s"[getBidStaticNoContent] -> No deals.",
      isServedAnyDeal = true
    )

    dspBidderResultModel.addCallStack(callStackModel)

    Some(dspBidderResultModel)
  }

  def getLastFailedDeals(request : DspBidderRequestModel): BidFailedReasonsModel = {
    val controller = request.controller
    val repositories = controller.repositories
    val auctionsTable  = repositories.auctions
    val bidResponseTable = repositories.bidResponses
    val bidRequestsTable =repositories.bidRequests

    val joins = for {
      (bidRequest, _)
    }

    bidResponseTable.filter(r => r.iswontheauction != true )

    val failedBidRequests = bidRequestsTable.filter(r=> r.iswontheauction != true)
    val sortedFailedRequests= failedBidRequests.sorted(w=> w.createddate)

  }

  override def getBidActual(
    request : DspBidderRequestModel) : Option[DspBidderResultModel] = ???

  override def getBidActualNoContent(
    request : DspBidderRequestModel) : Option[DspBidderResultModel] = ???
}
