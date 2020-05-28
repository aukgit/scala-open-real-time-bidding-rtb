package shared.com.ortb.model.config

case class DemandSideServiceModel(
  title : String,
  description : String,
  domain : String,
  port : Int,
  routing : Array[String],
  bidIncrementRangeModel : RangeDoubleModel
) extends ServiceBaseModel
