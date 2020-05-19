package shared.com.ortb.demadSidePlatforms.traits.properties

import shared.com.ortb.adapters.traits.{ BannerModelAdapter, ModelsAdapters, SiteModelAdapter }
import shared.com.ortb.demadSidePlatforms.DemandSidePlatformStaticBidResponseLogicImplementation
import shared.com.ortb.demadSidePlatforms.traits.logics.DemandSidePlatformStaticBidResponseLogic
import shared.com.ortb.manager.traits.{ CreateDefaultContext, DefaultExecutionContextManagerConcreteImplementation }
import shared.com.ortb.model.config.DemandSidePlatformConfigurationModel
import shared.io.helpers.AdapterHelper

import scala.concurrent.ExecutionContext

trait DemandSidePlatformBiddingProperties extends CreateDefaultContext {
  val demandSidePlatformConfiguration : DemandSidePlatformConfigurationModel

  lazy val defaultIncrementNumber : Double = demandSidePlatformConfiguration.defaultBidIncrementNumber
  lazy val defaultStaticDeal : Double = demandSidePlatformConfiguration.defaultBidStaticDeal
  lazy val noDealPrice : Double = 0
  lazy val modelsAdapters : ModelsAdapters = AdapterHelper.modelsAdapters
  lazy val bannerModelAdapter : BannerModelAdapter = modelsAdapters.bannerModelAdapter
  lazy val siteModelAdapter : SiteModelAdapter = modelsAdapters.siteModelAdapter
  lazy val executionContextManager = new DefaultExecutionContextManagerConcreteImplementation

  lazy val defaultLimit : Int = coreProperties.demandSidePlatformConfiguration.defaultGenericLimit
  lazy val defaultAdvertiseLimit : Int = coreProperties.demandSidePlatformConfiguration.defaultAdvertiseLimit
  lazy val demandSidePlatformStaticBidResponseLogic : DemandSidePlatformStaticBidResponseLogic = new
      DemandSidePlatformStaticBidResponseLogicImplementation(coreProperties)
  val coreProperties : DemandSidePlatformCorePropertiesContracts

  implicit override def createDefaultContext() : ExecutionContext =
    executionContextManager.defaultExecutionContext

  def isStatic : Boolean = demandSidePlatformConfiguration.isStaticSimulate
}

