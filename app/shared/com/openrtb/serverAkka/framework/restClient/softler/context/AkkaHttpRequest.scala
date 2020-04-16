package com.ortb.serverAkka.framework.restClient.softler.context

import com.ortb.serverAkka.framework.restClient.softler.client.{ClientRequest, RequestState}

trait AkkaHttpRequest {
  lazy val request : ClientRequest[RequestState.Idempotent] = ClientRequest(
    "http://localhost:8085/api/newServer")
}
