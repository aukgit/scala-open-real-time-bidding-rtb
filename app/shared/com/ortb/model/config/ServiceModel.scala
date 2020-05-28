package shared.com.ortb.model.config

import shared.com.ortb.model.config.core.ServiceBaseModel

case class ServiceModel(
  title : String,
  description : String,
  domain : String,
  port : Int,
  routing : Array[String]
) extends ServiceBaseModel
