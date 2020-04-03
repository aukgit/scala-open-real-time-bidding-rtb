package com.ortb.model.config

case class ServerInfoModel(
  domain: String,
  port: Int,
  agentServers: Array[ServerInfoModel]
)
