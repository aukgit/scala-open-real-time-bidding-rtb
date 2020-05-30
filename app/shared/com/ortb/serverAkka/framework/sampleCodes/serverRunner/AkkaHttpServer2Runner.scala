package shared.com.ortb.serverAkka.framework.sampleCodes.serverRunner

import shared.com.ortb.constants.AppConstants
import shared.com.ortb.serverAkka.framework.sampleCodes.AkkaServerDefinition

object AkkaHttpServer2Runner extends App {
  lazy val config = AppConstants.AppManager.config
  lazy val server = config.server
  lazy val services = server.services

  lazy val monitorServer = new AkkaServerDefinition(services.monitorService)
  monitorServer.serverRunAt()
}
