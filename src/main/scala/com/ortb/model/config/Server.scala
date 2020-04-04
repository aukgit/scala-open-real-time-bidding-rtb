package com.ortb.model.config

trait ServerInfoModelT

case class Server(
  domain: String,
  port: Int,
) extends ServerInfoModelT
