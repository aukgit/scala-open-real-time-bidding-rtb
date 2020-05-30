package shared.com.ortb.serverAkka.framework.sampleCodes

import shared.com.ortb.controllers.traits.AdditionalConfigBasedLogger
import shared.com.ortb.serverAkka.framework.ServerRun
import shared.com.ortb.serverAkka.framework.restClient.softler.context.{ AkkaHttpContext, AkkaHttpRequest, AkkaHttpResponse, AkkaHttpResponseHandler }

trait AkkHttpServerContracts
  extends AkkaHttpRequest
    with AkkaHttpResponse
    with AkkaHttpResponseHandler
    with AkkaHttpContext
    with AdditionalConfigBasedLogger
    with ServiceProperties
    with ServerRun
