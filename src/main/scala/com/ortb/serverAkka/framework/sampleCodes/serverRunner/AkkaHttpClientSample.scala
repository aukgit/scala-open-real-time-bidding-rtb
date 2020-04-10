package com.ortb.serverAkka.framework.sampleCodes.serverRunner

import com.ortb.serverAkka.framework.restClient.softler.context.{AkkaHttpResponse, AkkaHttpResponseHandler, AkkaHttpContext, AkkaHttpRequest}

/**
 * This sample shows how to fire a single request
 * The project configuration file will be included inside the client library
 */
object AkkaHttpClientSample
  extends App
    with AkkaHttpRequest
    with AkkaHttpResponse
    with AkkaHttpResponseHandler
    with AkkaHttpContext
