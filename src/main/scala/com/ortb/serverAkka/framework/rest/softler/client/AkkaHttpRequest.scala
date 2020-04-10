package com.ortb.serverAkka.framework.rest.softler.client

import akka.http.scaladsl.model.HttpRequest

/**
  * The akka http request data holder
  */
trait AkkaHttpRequest {
  def request: HttpRequest
}
