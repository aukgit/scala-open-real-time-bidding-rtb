package shared.com.ortb.model

case class HeightWidthModel(
  override val maybeHeight : Option[Int],
  override val maybeWidth : Option[Int]
) extends HeightWidthBaseModel(maybeHeight, maybeWidth)
