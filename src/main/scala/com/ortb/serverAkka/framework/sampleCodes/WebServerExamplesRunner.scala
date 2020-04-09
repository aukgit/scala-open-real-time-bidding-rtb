package com.ortb.serverAkka.framework.sampleCodes

object WebServerExamplesRunner extends App {
  val sampleServer = new SampleServer
  sampleServer.startServerAt(8087)
}