package shared.com.ortb.demadSidePlatforms

import shared.com.ortb.demadSidePlatforms.traits.logics.{ DemandSidePlatformBiddingLogic, DemandSidePlatformStaticBidResponseLogic }
import shared.com.ortb.demadSidePlatforms.traits.properties.{ DemandSidePlatformBiddingProperties, DemandSidePlatformCorePropertiesContracts }
import shared.com.ortb.enumeration.DemandSidePlatformBiddingAlgorithmType.DemandSidePlatformBiddingAlgorithmType
import shared.com.ortb.manager.AppManager
import shared.com.ortb.manager.traits.DefaultExecutionContextManager
import shared.com.ortb.model._
import shared.com.ortb.model.config.DemandSidePlatformConfigurationModel
import shared.com.ortb.model.results.DspBidderRequestModel
import shared.io.helpers.EmptyValidateHelper

class DemandSidePlatformBiddingAgent(
  val coreProperties : DemandSidePlatformCorePropertiesContracts,
  algorithmType : DemandSidePlatformBiddingAlgorithmType)
  extends DemandSidePlatformBiddingLogic
    with DemandSidePlatformBiddingProperties
    with DefaultExecutionContextManager
    with AddNewAdvertiseOnNotFound
    with BiddableInfoModelsGetter
    with AdvertiseQueryAppendLogic
    with FailedBidsGetter with ImpressionDealsGetter with ExactHeightWidthQueryRowsGetter with DefaultActualNoContentResponse {

  lazy val defaultLimit : Int = coreProperties.demandSidePlatformConfiguration.defaultGenericLimit
  lazy val defaultAdvertiseLimit : Int = coreProperties.demandSidePlatformConfiguration.defaultAdvertiseLimit
  lazy val demandSidePlatformStaticBidResponseLogic : DemandSidePlatformStaticBidResponseLogic = new
      DemandSidePlatformStaticBidResponseLogicImplementation(coreProperties)

  override def getBidStatic(request : DspBidderRequestModel) : Option[DemandSidePlatformBidResponseModel] =
    demandSidePlatformStaticBidResponseLogic.getBidStatic(request)

  override def isStatic : Boolean = demandSidePlatformConfiguration.isStaticSimulate

  lazy override val demandSidePlatformConfiguration : DemandSidePlatformConfigurationModel =
    coreProperties.demandSidePlatformConfiguration

  override def getBidStaticNoContent(request : DspBidderRequestModel) : Option[DemandSidePlatformBidResponseModel] =
    demandSidePlatformStaticBidResponseLogic.getBidStaticNoContent(request)

  lazy override val appManager : AppManager = coreProperties.appManager

  override def getBidPrices(
    request : DspBidderRequestModel) : Option[DemandSidePlatformBidResponseModel] = ???

  override def getBidActual(
    request : DspBidderRequestModel) : Option[DemandSidePlatformBidResponseModel] = {
    val biddableImpressionInfoModels = getBiddableImpressionInfoModels(
      request : DspBidderRequestModel)

    val impressionDeals =
      getImpressionInfoModelsFromImpressionBiddableInfoModels(
        request, biddableImpressionInfoModels)

    if (EmptyValidateHelper.isItemsEmpty(impressionDeals)) {
      return getBidActualNoContent(request)
    }

    // has something
    //    val has = impressionDeals.get.map(w => w.)


    throw new NotImplementedError()
  }
}
