package shared.com.ortb.model

case class HeightWidthModel(
  maybeHeight : Option[Int],
  maybeWidth : Option[Int]
) extends HeightWidthBaseModel(maybeHeight, maybeWidth)


