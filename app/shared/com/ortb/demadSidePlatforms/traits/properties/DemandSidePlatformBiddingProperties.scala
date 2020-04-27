package shared.com.ortb.demadSidePlatforms.traits.properties

import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config.DemandSidePlatformConfigurationModel

import scala.concurrent.ExecutionContext

trait DemandSidePlatformBiddingProperties {
  val demandSidePlatformConfiguration : DemandSidePlatformConfigurationModel

  lazy val defaultIncrementNumber : Double = demandSidePlatformConfiguration.defaultBidIncrementNumber
  lazy val defaultStaticDeal : Double = demandSidePlatformConfiguration.defaultBidStaticDeal
  lazy val noDealPrice : Double = 0
}

