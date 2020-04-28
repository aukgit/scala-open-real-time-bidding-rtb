package shared.com.ortb.demadSidePlatforms.traits.properties

import controllers.rtb.traits.properties.ServiceControllerCorePropertiesContracts
import shared.com.ortb.model.config.{ DemandSidePlatformConfigurationModel, ServiceModel }
import shared.com.ortb.persistent.Repositories
import shared.com.ortb.persistent.repositories.DemandSidePlatformRepository
import shared.com.ortb.persistent.schema.Tables
import shared.io.loggers.{ DatabaseLogTracer, DatabaseLogTracerImplementation }

trait DemandSidePlatformCorePropertiesContracts
  extends ServiceControllerCorePropertiesContracts with BiddingDefaultProperties with DemandSidePlatformBiddingProperties {
  val demandSideId : Int

  lazy override val databaseLogger : DatabaseLogTracer = new DatabaseLogTracerImplementation(appManager)
  val repositories : Repositories

  lazy override val currentServiceModel : ServiceModel =
    services.demandSidePlatForms(demandSideId - 1)

  lazy val demandSidePlatformRepository : DemandSidePlatformRepository =
    repositories.demandSidePlatformRepository
  lazy val demandSidePlatformEntity : Option[Tables.DemandsideplatformRow] = demandSidePlatformRepository
    .getById(demandSideId)
  lazy val demandSidePlatformJson : Option[String] = demandSidePlatformRepository
    .fromEntityToJson(demandSidePlatformEntity)

  lazy val demandSidePlatformConfiguration : DemandSidePlatformConfigurationModel =
    config.server.demandSidePlatformConfiguration
}
