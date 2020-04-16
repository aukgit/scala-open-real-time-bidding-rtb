package com.ortb.serverAkka.framework.sampleCodes.serverRunner

import com.ortb.serverAkka.framework.sampleCodes.SampleServer

object WebServerExamplesRunner extends App {
  val sampleServer = new SampleServer
  sampleServer.startServerAt(8087)
}
