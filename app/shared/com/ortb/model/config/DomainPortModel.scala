package shared.com.ortb.model.config

import shared.com.ortb.model.config.core.DomainPortBaseModel

case class DomainPortModel(
  domainHost : String,
  port : Int
) extends DomainPortBaseModel
