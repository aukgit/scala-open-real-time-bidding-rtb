package shared.com.ortb.model.dimensions

import shared.io.helpers.EmptyValidateHelper

abstract class HeightWidthBaseModel(
  val maybeHeight : Option[Int],
  val maybeWidth : Option[Int]) {
  lazy val hasHeight : Boolean =
    EmptyValidateHelper.isDefinedIntPlusPositive(
      maybeHeight)

  lazy val hasWidth : Boolean =
    EmptyValidateHelper.isDefinedIntPlusPositive(
      maybeWidth)
  lazy val width : Int =
    if (hasWidth) maybeWidth.get else 0

  lazy val height : Int =
    if (hasHeight) maybeHeight.get else 0

  lazy val isEmptyHeightWidth : Boolean = !hasWidth && !hasHeight
}
