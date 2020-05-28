package shared.com.ortb.demadSidePlatforms.traits.properties

import shared.com.ortb.constants.AppConstants
import shared.com.ortb.controllers.traits.properties.ServiceControllerCorePropertiesContracts
import shared.com.ortb.model.config.{ DemandSidePlatformConfigurationModel, DemandSideServiceModel, RangeDoubleModel, ServiceBaseModel }
import shared.com.ortb.persistent.Repositories
import shared.com.ortb.persistent.repositories.DemandSidePlatformRepository
import shared.com.ortb.persistent.schema.Tables
import shared.io.loggers.{ DatabaseLogTracer, DatabaseLogTracerImplementation }

trait DemandSidePlatformCorePropertiesContracts
  extends ServiceControllerCorePropertiesContracts
    with BiddingDefaultProperties
    with DemandSidePlatformBiddingProperties {
  lazy val randomNumberIncrementerGuessRange : RangeDoubleModel =
    demandSidePlatformConfiguration.randomNumberIncrementerGuessRange

  lazy override val databaseLogger : DatabaseLogTracer = new DatabaseLogTracerImplementation(
    appManager,
    this.getClass.getName)

  lazy override val currentServiceModel : ServiceBaseModel = demandSideServiceModel

  lazy val demandSideServiceModel : DemandSideServiceModel =
    services.demandSidePlatforms(demandSideId - 1)

  lazy val demandSidePlatformRepository : DemandSidePlatformRepository =
    repositories.demandSidePlatformRepository
  lazy val demandSidePlatformEntity : Option[Tables.DemandsideplatformRow] = demandSidePlatformRepository
    .getById(demandSideId)
  lazy val demandSidePlatformJson : Option[String] = demandSidePlatformRepository
    .fromEntityToJson(demandSidePlatformEntity)

  lazy val demandSidePlatformConfiguration : DemandSidePlatformConfigurationModel =
    config.server.demandSidePlatformConfiguration
  lazy val repositories : Repositories = AppConstants.Repositories
}
