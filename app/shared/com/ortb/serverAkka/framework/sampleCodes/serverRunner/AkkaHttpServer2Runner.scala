package shared.com.ortb.serverAkka.framework.sampleCodes.serverRunner

import shared.com.ortb.serverAkka.AkkaSimpleMessagingMethods
import shared.com.ortb.serverAkka.framework.sampleCodes.AkkaServerDefinition
import shared.com.ortb.serverAkka.traits.ServiceProperties

object AkkaHttpServer2Runner extends App with ServiceProperties {
  lazy override val currentServiceModel = services.monitorService
  lazy val akkaSimpleMessagingMethods = new AkkaSimpleMessagingMethods
  lazy val monitorServer = new AkkaServerDefinition(
    currentServiceModel,
    akkaSimpleMessagingMethods)
  monitorServer.serverRun()
}
