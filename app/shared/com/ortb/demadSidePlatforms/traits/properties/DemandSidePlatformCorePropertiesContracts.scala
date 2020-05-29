package shared.com.ortb.demadSidePlatforms.traits.properties

import play.api.http
import play.api.mvc.ResponseHeader
import shared.com.ortb.constants.AppConstants
import shared.com.ortb.controllers.traits.properties.ServiceControllerCorePropertiesContracts
import shared.com.ortb.model.config.{ DemandSidePlatformConfigurationModel, DemandSideServiceModel }
import shared.com.ortb.model.ranges.RangeDoubleModel
import shared.com.ortb.persistent.Repositories
import shared.com.ortb.persistent.repositories.DemandSidePlatformRepository
import shared.com.ortb.persistent.schema.Tables
import shared.io.loggers.{ DatabaseLogTracer, DatabaseLogTracerImplementation }

trait DemandSidePlatformCorePropertiesContracts
  extends ServiceControllerCorePropertiesContracts
    with BiddingDefaultProperties
    with DemandSidePlatformBiddingProperties {
  lazy val globalRandomRange : RangeDoubleModel =
    demandSidePlatformConfiguration.globalRandomRange

  lazy override val databaseLogger : DatabaseLogTracer = new DatabaseLogTracerImplementation(
    appManager,
    this.getClass.getName)

  lazy override val currentServiceModel : DemandSideServiceModel =
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

  lazy val defaultOkResponseHeader : ResponseHeader =
    ResponseHeader(
      http.Status.OK,
      headers = coreProperties.commonResponseHeaders)

  lazy val defaultNoResponseHeader : ResponseHeader =
    ResponseHeader(
      http.Status.NO_CONTENT,
      headers = coreProperties.commonResponseHeaders)
}
