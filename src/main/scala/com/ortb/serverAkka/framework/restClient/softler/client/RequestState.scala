package com.ortb.serverAkka.framework.restClient.softler.client

import scala.annotation.implicitNotFound

object RequestState {

  sealed trait Idempotent extends RequestState

  sealed trait NotReady extends RequestState

  sealed trait EntityAcceptance extends RequestState

  @implicitNotFound("The request is not idempotent. you need for example: GET, DELETE ...")
  type RequestIsIdempotent[S] = S =:= RequestState.Idempotent

  @implicitNotFound("Request is already built")
  type RequestNotReady[S] = S =:= RequestState.NotReady

  @implicitNotFound("A HTTP entity is required for this request type")
  type RequestWithEntity[S] = S =:= RequestState.EntityAcceptance
}

/**
  * The evidences to build a [[ClientRequest]]
  */
sealed trait RequestState