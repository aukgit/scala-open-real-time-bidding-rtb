package shared.com.ortb.model

case class MinMaxHeightWidthModel(
  minHeightWidth : HeightWidthBaseModel,
  heightWidth : HeightWidthBaseModel,
  maxHeightWidth : HeightWidthBaseModel
) extends HeightWidthBaseModel(heightWidth.maybeHeight, heightWidth.maybeWidth) {
  lazy val minHeight : Int = minHeightWidth.height
  lazy val minWidth : Int = minHeightWidth.width
  lazy val isMinHeightWidthEmpty : Boolean = minHeightWidth.isEmptyHeightWidth

  lazy val maxHeight : Int = maxHeightWidth.height
  lazy val maxWidth : Int = maxHeightWidth.width
  lazy val isMaxHeightWidthEmpty : Boolean = maxHeightWidth.isEmptyHeightWidth
}
