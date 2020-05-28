package shared.com.ortb.model.config

case class ServiceModel(
  title : String,
  description : String,
  domain : String,
  port : Int,
  routing : Array[String]
) extends ServiceBaseModel


case class DemandSideServiceModel(
  title : String,
  description : String,
  domain : String,
  port : Int,
  routing : Array[String],
  demandSidePlatformId : Int,
  bidIncrementRangeModel : RangeDoubleModel
) extends ServiceBaseModel