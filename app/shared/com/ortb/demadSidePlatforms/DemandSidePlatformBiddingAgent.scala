package shared.com.ortb.demadSidePlatforms

import shared.com.ortb.demadSidePlatforms.traits.logics.{ DemandSidePlatformBiddingLogic, DemandSidePlatformStaticBidResponseLogic }
import shared.com.ortb.demadSidePlatforms.traits.properties.{ DemandSidePlatformBiddingProperties, DemandSidePlatformCoreProperties }
import shared.com.ortb.enumeration.DemandSidePlatformBiddingAlgorithmType.DemandSidePlatformBiddingAlgorithmType
import shared.com.ortb.model
import shared.com.ortb.model._
import shared.com.ortb.model.config.DemandSidePlatformConfigurationModel
import shared.com.ortb.model.results.DspBidderRequestModel
import shared.io.helpers.EmptyValidateHelper
import slick.jdbc.SQLiteProfile.api._

import scala.collection.mutable.ArrayBuffer

class DemandSidePlatformBiddingAgent(
  coreProperties : DemandSidePlatformCoreProperties,
  algorithmType : DemandSidePlatformBiddingAlgorithmType)
  extends DemandSidePlatformBiddingLogic with DemandSidePlatformBiddingProperties {

  lazy val demandSidePlatformStaticBidResponseLogic : DemandSidePlatformStaticBidResponseLogic = new
      DemandSidePlatformStaticBidResponseLogicImplementation(coreProperties)

  override def getBidPrices(
    request : DspBidderRequestModel) : Option[DspBidderResultModel] = ???


  def getLastFailedDeals(
    request : DspBidderRequestModel,
    limit : Int = 5) : BidFailedReasonsModel = {
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

  def biddableImpressionInfo(request : DspBidderRequestModel) : Array[ImpressionBiddableInfoModel] = {
    val repositories = coreProperties.repositories
    val advertises = repositories.advertises
    val impressions = request.bidRequest.imp.get

    val list = new ArrayBuffer[ImpressionBiddableInfoModel](impressions.length)

    impressions.foreach(impression => {
      if (EmptyValidateHelper.isEmpty(impression.banner)) {
        list += ImpressionBiddableInfoModel(
          impression,
          None,
          ImpressionBiddableAttributesModel(
            isBiddable = false,
            hasBanner = false,
            0))

//        return
      }

      val banner = impression.banner.get
      var advertisesQuery = advertises.filter(advertise =>
        advertise.isvideo === 0)

//      if (EmptyValidateHelper.isDefined(banner.wmax)){
//        advertisesQuery = advertisesQuery.filter(ad => ad.maxweight)
//      }

      // val hasMaxMin = EmptyValidateHelper.isAllDefinedFromMultiple(banner.get.hmax,banner.get.wmax)

    })


    throw new NotImplementedError()
  }

  override def getBidActual(
    request : DspBidderRequestModel) : Option[DspBidderResultModel] = {
    //    val isCurrentRequestBiddable = biddableImpressionInfo(
    //      request: DspBidderRequestModel)
    throw new NotImplementedError()
  }

  override def getBidActualNoContent(
    request : DspBidderRequestModel) : Option[DspBidderResultModel] = {
    val dspBidderResultModel =
      model.DspBidderResultModel(request, request.bidRequest, isNoContent = true)

    val callStackModel = CallStackModel(
      deal = coreProperties.noDealPrice,
      performingAction = s"[getBidActualNoContent] -> No deals."
    )

    dspBidderResultModel.addCallStack(callStackModel)

    Some(dspBidderResultModel)
  }

  override def getBidStatic(request : DspBidderRequestModel) : Option[DspBidderResultModel] =
    demandSidePlatformStaticBidResponseLogic.getBidStatic(request)

  override def isStatic : Boolean = demandSidePlatformConfiguration.isStaticSimulate

  lazy override val demandSidePlatformConfiguration : DemandSidePlatformConfigurationModel =
    coreProperties.demandSidePlatformConfiguration

  override def getBidStaticNoContent(request : DspBidderRequestModel) : Option[DspBidderResultModel] =
    demandSidePlatformStaticBidResponseLogic.getBidStaticNoContent(request)
}
