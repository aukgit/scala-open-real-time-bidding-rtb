package com.ortb.model.config

case class ServiceModel(
  title       : String,
  description : String,
  domain      : String,
  port        : Int,
  routing     : Array[String]
)
