package com.ortb.serverAkka.framework.rest.softler.exception

/**
  * The root rest exception
  * This class is intended to match any error during a REST call
  */
class RestException(msg: String) extends Exception(msg)

/**
  * Will be raised when a 'informational' error occurred
  * In the future this can be handled with other ResponseProcessor strategies
  * 100 to 200
  */
case class InformationalErrorRestException(msg: String) extends RestException(msg)

/**
  * Will be raised when a 'redirection' error occurred
  * In the future this can be handled with other ResponseProcessor strategies
  * 300 to 400
  */
case class RedirectionErrorRestException(msg: String) extends RestException(msg)

/**
  * Will be raised when a client error occurred
  * 400 to 499
  */
case class ClientErrorRestException(msg: String) extends RestException(msg)

/**
  * Will be raised when a server error occurred
  * 500 to 599
  */
case class ServerErrorRestException(msg: String) extends RestException(msg)

/**
  * Will be raised when a status code is not known
  * ???
  */
case class CustomRestException(msg: String) extends RestException(msg)
