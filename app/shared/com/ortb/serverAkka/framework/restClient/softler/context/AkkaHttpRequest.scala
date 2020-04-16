package shared.com.ortb.serverAkka.framework.restClient.softler.context

import shared.com.ortb.serverAkka.framework.restClient.softler.client.{RequestState, ClientRequest}

trait AkkaHttpRequest {
  lazy val request : ClientRequest[RequestState.Idempotent] = ClientRequest(
    "http://localhost:8085/api/newServer")
}
