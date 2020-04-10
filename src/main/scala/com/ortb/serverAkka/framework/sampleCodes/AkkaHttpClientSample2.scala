package com.ortb.serverAkka.framework.sampleCodes

object AkkaHttpClientSample2 extends App {
  val server = new AkkaServerDefinition("newServer")
  server.serverRunAt(8085)
}
