package shared.com.ortb.demadSidePlatforms

import shared.com.ortb.adapters.traits.BannerModelAdapter
import shared.com.ortb.constants.AppConstants
import shared.com.ortb.demadSidePlatforms.traits.logics.{ DemandSidePlatformBiddingLogic, DemandSidePlatformStaticBidResponseLogic }
import shared.com.ortb.demadSidePlatforms.traits.properties.{ DemandSidePlatformBiddingProperties, DemandSidePlatformCorePropertiesContracts }
import shared.com.ortb.enumeration.DemandSidePlatformBiddingAlgorithmType.DemandSidePlatformBiddingAlgorithmType
import shared.com.ortb.importedModels.biddingRequests._
import shared.com.ortb.importedModels.campaign.SimpleBanner
import shared.com.ortb.manager.AppManager
import shared.com.ortb.manager.traits.DefaultExecutionContextManager
import shared.com.ortb.model._
import shared.com.ortb.model.config.DemandSidePlatformConfigurationModel
import shared.com.ortb.model.results.DspBidderRequestModel
import shared.com.ortb.persistent
import shared.com.ortb.persistent.repositories.AdvertiseRepository
import shared.com.ortb.persistent.schema
import shared.com.ortb.persistent.schema.Tables
import shared.com.ortb.persistent.schema.Tables._
import shared.com.repository.traits.FutureToRegular
import shared.io.helpers.{ AdapterHelper, EmptyValidateHelper, NumberHelper }
import shared.io.loggers.AppLogger
import slick.dbio.Effect
import slick.jdbc
import slick.jdbc.SQLiteProfile
import slick.jdbc.SQLiteProfile.api._
import slick.sql.FixedSqlStreamingAction

import scala.concurrent.Future

class DemandSidePlatformBiddingAgent(
  val coreProperties : DemandSidePlatformCorePropertiesContracts,
  algorithmType : DemandSidePlatformBiddingAlgorithmType)
  extends DemandSidePlatformBiddingLogic
    with DemandSidePlatformBiddingProperties
    with DefaultExecutionContextManager
    with addNewAdvertiseOnNotFound
    with BiddableInfoModelsGetter
    with AdvertiseQueryAppendLogic
    with FailedBidsGetter {

  lazy val defaultLimit : Int = coreProperties.demandSidePlatformConfiguration.defaultGenericLimit
  lazy val defaultAdvertiseLimit : Int = coreProperties.demandSidePlatformConfiguration.defaultAdvertiseLimit
  lazy val demandSidePlatformStaticBidResponseLogic : DemandSidePlatformStaticBidResponseLogic = new
      DemandSidePlatformStaticBidResponseLogicImplementation(coreProperties)

  override def getBidPrices(
    request : DspBidderRequestModel) : Option[DemandSidePlatformBidResponseModel] = ???

  def getExactHeightWidthQueryRows(
    advertisesQuery : Query[Advertise, AdvertiseRow, Seq],
    banner : BannerModel): Option[Seq[AdvertiseRow]] = {
    throw new NotImplementedError()
  }

  def getImpressionDealsFromBiddableImpressionInfoModels(
    request : DspBidderRequestModel,
    biddableImpressionInfoModels : Seq[ImpressionBiddableInfoModel]) :
  Option[List[ImpressionDealModel]] = {
    if (EmptyValidateHelper.isItemsEmpty(Some(biddableImpressionInfoModels))) {
      return None
    }

    val list = biddableImpressionInfoModels.map(b => {
      val attr = b.attributes
      val isEmpty = !attr.isBiddable && attr.advertisesFoundCount == 0
      addNewAdvertiseIfNoAdvertiseInTheGivenCriteria(
        request,
        isEmpty,
        b)
    }).toList

    // create deals
    biddableImpressionInfoModels.map(w => {
      if (!w.attributes.isBiddable) {
        return None
      }
      val banner = w.impression.banner.get
      var advs = w.advertises.get.to(LazyList)

      if (banner.h.isDefined) {
        advs = advs.filter(r => banner.h.get == r.height)
      }

      if (banner.w.isDefined) {
        advs = advs.filter(r => banner.w.get == r.width)
      }

    })

    Some(list)
    throw new NotImplementedError()
  }

  override def getBidActual(
    request : DspBidderRequestModel) : Option[DemandSidePlatformBidResponseModel] = {
    val biddableImpressionInfoModels = getBiddableImpressionInfoModels(
      request : DspBidderRequestModel)

    val impressionDeals =
      getImpressionDealsFromBiddableImpressionInfoModels(
        request, biddableImpressionInfoModels)

    if (EmptyValidateHelper.isItemsEmpty(impressionDeals)) {
      return getBidActualNoContent(request)
    }

    // has something
    //    val has = impressionDeals.get.map(w => w.)


    throw new NotImplementedError()
  }

  override def getBidActualNoContent(
    request : DspBidderRequestModel) : Option[DemandSidePlatformBidResponseModel] = {
    val dspBidderResultModel =
      DemandSidePlatformBidResponseModel(request, request.bidRequest, isNoContent = true)

    val callStackModel = CallStackModel(
      deal = coreProperties.noDealPrice,
      performingAction = s"[getBidActualNoContent] -> No deals."
    )

    dspBidderResultModel.addCallStack(callStackModel)

    Some(dspBidderResultModel)
  }

  override def getBidStatic(request : DspBidderRequestModel) : Option[DemandSidePlatformBidResponseModel] =
    demandSidePlatformStaticBidResponseLogic.getBidStatic(request)

  override def isStatic : Boolean = demandSidePlatformConfiguration.isStaticSimulate

  lazy override val demandSidePlatformConfiguration : DemandSidePlatformConfigurationModel =
    coreProperties.demandSidePlatformConfiguration

  override def getBidStaticNoContent(request : DspBidderRequestModel) : Option[DemandSidePlatformBidResponseModel] =
    demandSidePlatformStaticBidResponseLogic.getBidStaticNoContent(request)

  lazy override val appManager : AppManager = coreProperties.appManager
}
