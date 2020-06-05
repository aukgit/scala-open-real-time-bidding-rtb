package shared.com.ortb.serverAkka.framework.sampleCodes.serverRunner

import shared.com.ortb.serverAkka._
import shared.com.ortb.serverAkka.framework.sampleCodes._
import shared.com.ortb.serverAkka.implementations.AkkaMonitorServiceMethods
import shared.com.ortb.serverAkka.traits._

object AkkaHttpServer2Runner extends App with ServiceProperties {
  lazy override val currentServiceModel = services.monitorService
  //  lazy val akkaSimpleMessagingMethods = new AkkaSimpleMessagingMethods
  lazy val akkaMonitorStartServiceGetPostConcreteMethod = new AkkaMonitorServiceMethods
  lazy val monitorServer = new AkkaServerDefinition(
    currentServiceModel,
    akkaMonitorStartServiceGetPostConcreteMethod)
  monitorServer.serverRun()
}
