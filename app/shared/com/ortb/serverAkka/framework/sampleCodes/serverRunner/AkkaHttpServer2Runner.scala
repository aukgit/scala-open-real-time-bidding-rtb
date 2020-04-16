package com.ortb.serverAkka.framework.sampleCodes.serverRunner

import com.ortb.serverAkka.framework.sampleCodes.AkkaServerDefinition

object AkkaHttpServer2Runner extends App {
  val server = new AkkaServerDefinition("newServer")
  server.serverRunAt(8085)
}
