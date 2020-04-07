package com.ortb.model.config

case class ServiceModel(
  routing: Array[String],
  domain: String,
  port: Int,
  title: String,
  description: String
)
