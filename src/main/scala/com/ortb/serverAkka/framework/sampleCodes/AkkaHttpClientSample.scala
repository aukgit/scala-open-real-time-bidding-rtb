package com.ortb.serverAkka.framework.sampleCodes

import com.ortb.serverAkka.framework.rest.softler.context._


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
