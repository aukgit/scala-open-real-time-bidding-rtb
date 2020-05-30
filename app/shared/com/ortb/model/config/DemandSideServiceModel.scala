package shared.com.ortb.model.config

import shared.com.ortb.model.config.core.ServiceBaseModel
import shared.com.ortb.model.ranges.RangeDoubleModel

case class DemandSideServiceModel(
  title : String,
  prefixRouting : String,
  description : String,
  domain : String,
  port : Int,
  routing : Array[String],
  ownBiddingRandomRange : RangeDoubleModel
) extends ServiceBaseModel
