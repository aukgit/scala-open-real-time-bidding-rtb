package com.ortb.serverAkka.framework.rest.softler.client

/**
  * The http methods implementation which can be mixed in
  */
trait Methods[R <: RequestState] extends IdempotentMethods[R] with UnsafeMethods[R]
