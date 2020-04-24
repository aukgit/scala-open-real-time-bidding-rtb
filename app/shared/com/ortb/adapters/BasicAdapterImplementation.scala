package shared.com.ortb.adapters
import io.circe.generic.semiauto._
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.Decoder.AccumulatingResult
import io.circe.generic.JsonCodec

import shared.com.ortb.adapters.traits.BasicAdapter
import shared.com.ortb.constants.AppConstants
import shared.io.helpers.EmptyValidateHelper

trait BasicAdapterImplementation extends BasicAdapter {
  def convertItemTo[A, B](
    item : Option[A],
    adapterLogic : Option[A] => Option[B]) : Option[B] = {
    val isEmpty =
      EmptyValidateHelper.isEmpty(item, Some(AppConstants.NoContent))

    if (isEmpty) {
      return None
    }

    adapterLogic(item)
  }

  def convertItemsTo[A, B](
    items : Option[Iterable[A]],
    adapterLogic : Option[Iterable[A]] => Option[Iterable[B]])
  : Option[Iterable[B]] = {
    val isEmpty =
      EmptyValidateHelper.isEmpty(items, Some(AppConstants.NoContent))

    if (isEmpty) {
      return None
    }

    adapterLogic(items)
  }
}
