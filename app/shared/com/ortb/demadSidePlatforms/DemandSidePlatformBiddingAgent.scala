package shared.com.ortb.demadSidePlatforms

import shared.com.ortb.enumeration.DemandSidePlatformBiddingAlgorithmType.DemandSidePlatformBiddingAlgorithmType
import shared.com.ortb.model
import shared.com.ortb.model.results.DspBidderRequestModel
import shared.com.ortb.model._
import shared.io.helpers.EmptyValidateHelper
import slick.jdbc.SQLiteProfile.api._





class DemandSidePlatformBiddingAgent(
  coreProperties : DemandSidePlatformCoreProperties,
  algorithmType: DemandSidePlatformBiddingAlgorithmType)
    extends DemandSidePlatformBiddingLogic {

  override def getBidPrices(
      request: DspBidderRequestModel): Option[DspBidderResultModel] = ???

  override def getBidStaticNoContent(
      request: DspBidderRequestModel): Option[DspBidderResultModel] = {
    val dspBidderResultModel =
      model.DspBidderResultModel(request, request.bidRequest, isNoContent = true)

    val callStackModel = CallStackModel(
      deal = coreProperties.noDealPrice,
      performingAction = s"[getBidStaticNoContent] -> No deals.",
      isServedAnyDeal = false
    )

    dspBidderResultModel.addCallStack(callStackModel)

    Some(dspBidderResultModel)
  }

  def getLastFailedDeals(request: DspBidderRequestModel,
                         limit: Int = 5): BidFailedReasonsModel = {
    val demandSidePlatformId = coreProperties.demandSideId
    val repositories = coreProperties.repositories
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

  def biddableImpressionInfo(request: DspBidderRequestModel): Array[ImpressionBiddableInfoModel] = {
    val repositories = coreProperties.repositories
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
      model.DspBidderResultModel(request, request.bidRequest, isNoContent = true)

    val callStackModel = CallStackModel(
      deal = coreProperties.noDealPrice,
      performingAction = s"[getBidActualNoContent] -> No deals."
    )

    dspBidderResultModel.addCallStack(callStackModel)

    Some(dspBidderResultModel)
  }

  override def getBidStatic(request : DspBidderRequestModel) : Option[DspBidderResultModel] = ???
}
