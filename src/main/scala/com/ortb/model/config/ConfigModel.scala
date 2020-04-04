package com.ortb.model.config

//sealed class HttpMethod extends Enumeration {
//  type HttpMethod = Value
//  val PUT, POST, DELETE, GET = Value
//}
//
//case class CommandModel(
//  action: String,
//  description: String,
//  urlSuffix: String,
//  httpMethod: HttpMethod
//)

case class ConfigModel(
  applicationName: String,
  displayVersion: String,
  server: Server,
  isDebug: Boolean,
  isLogError: Boolean,
  // commands: Array[CommandModel],
  author: String
)
