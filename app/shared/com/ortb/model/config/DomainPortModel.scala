package shared.com.ortb.model.config

import shared.com.ortb.model.config.core.DomainPortBaseModel

case class DomainPortModel(
  domain : String,
  port : Int,
  prefixRouting : String
) extends DomainPortBaseModel
