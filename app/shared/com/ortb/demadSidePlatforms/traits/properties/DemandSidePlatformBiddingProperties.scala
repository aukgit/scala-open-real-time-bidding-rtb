package shared.com.ortb.demadSidePlatforms.traits.properties

import shared.com.ortb.adapters.traits.{ BannerModelAdapter, ModelsAdapters, SiteModelAdapter }
import shared.com.ortb.constants.AppConstants
import shared.com.ortb.demadSidePlatforms.DemandSidePlatformStaticBidResponseLogicImplementation
import shared.com.ortb.demadSidePlatforms.traits.logics.DemandSidePlatformStaticBidResponseLogic
import shared.com.ortb.manager.traits.DefaultExecutionContextManagerConcreteImplementation
import shared.com.ortb.model.config.DemandSidePlatformConfigurationModel
import shared.io.helpers.AdapterHelper

trait DemandSidePlatformBiddingProperties {
  val demandSideId : Int
  val coreProperties : DemandSidePlatformCorePropertiesContracts

  lazy val defaultLimit : Int = this.demandSidePlatformConfiguration.defaultGenericLimit
  val demandSidePlatformConfiguration : DemandSidePlatformConfigurationModel
  lazy val defaultAdvertiseLimit : Int = this.demandSidePlatformConfiguration.defaultAdvertiseLimit

  lazy val defaultIncrementNumber : Double = demandSidePlatformConfiguration.defaultBidIncrementNumber
  lazy val defaultStaticDeal : Double = demandSidePlatformConfiguration.defaultBidStaticDeal
  lazy val noDealPrice : Double = 0
  lazy val modelsAdapters : ModelsAdapters = AdapterHelper.modelsAdapters
  lazy val bannerModelAdapter : BannerModelAdapter = modelsAdapters.bannerModelAdapter
  lazy val siteModelAdapter : SiteModelAdapter = modelsAdapters.siteModelAdapter
  lazy val executionContextManager = new DefaultExecutionContextManagerConcreteImplementation
  lazy val demandSidePlatformStaticBidResponseLogic : DemandSidePlatformStaticBidResponseLogic = new
      DemandSidePlatformStaticBidResponseLogicImplementation(demandSideId, coreProperties)

  def isStatic : Boolean = demandSidePlatformConfiguration.isStaticSimulate

  lazy val commonHeaders : Map[String, String] = coreProperties
    .config
    .server
    .requestDefaultHeaders
    .map(w => w.key -> w.value).toMap
}

