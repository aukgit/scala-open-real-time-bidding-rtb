package shared.com.ortb.model.config

import shared.com.ortb.model.config.core.ServiceBaseModel

case class ServiceModel(
  title : String,
  prefixRouting : String,
  description : String,
  domainHost : String,
  port : Int,
  routing : Array[String]
) extends ServiceBaseModel
