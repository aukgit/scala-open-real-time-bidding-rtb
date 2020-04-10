package com.ortb.serverAkka.framework.restClient.softler.client

import akka.http.scaladsl.model.HttpRequest

/**
  * The akka http request data holder
  */
trait AkkaHttpRequest {
  def request: HttpRequest
}
