package com.ortb.serverAkka.framework.rest.softler.context

import com.ortb.serverAkka.framework.rest.softler.client.{RequestState, ClientRequest}

trait AkkaHttpRequest {
  lazy val request: ClientRequest[RequestState.Idempotent] = ClientRequest(
    "https://github.com/Freshwood/akka-http-rest-client/blob/master/README.md")
}
