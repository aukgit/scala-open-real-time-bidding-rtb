package shared.com.ortb.demadSidePlatforms.traits.properties

import shared.com.ortb.adapters.traits.{ BannerModelAdapter, ModelsAdapters, SiteModelAdapter }
import shared.com.ortb.manager.traits.DefaultExecutionContextManagerConcreteImplementation
import shared.com.ortb.model.config.DemandSidePlatformConfigurationModel
import shared.io.helpers.AdapterHelper

trait DemandSidePlatformBiddingProperties {
  val demandSidePlatformConfiguration : DemandSidePlatformConfigurationModel

  lazy val defaultIncrementNumber : Double = demandSidePlatformConfiguration.defaultBidIncrementNumber
  lazy val defaultStaticDeal : Double = demandSidePlatformConfiguration.defaultBidStaticDeal
  lazy val noDealPrice : Double = 0
  lazy val modelsAdapters : ModelsAdapters = AdapterHelper.modelsAdapters
  lazy val bannerModelAdapter : BannerModelAdapter = modelsAdapters.bannerModelAdapter
  lazy val siteModelAdapter : SiteModelAdapter = modelsAdapters.siteModelAdapter
  lazy val executionContextManager = new DefaultExecutionContextManagerConcreteImplementation
}

