package shared.com.ortb.adapters

import shared.com.ortb.adapters.traits.BasicAdapter
import shared.com.ortb.constants.AppConstants
import shared.io.helpers.EmptyValidateHelper

class BasicAdapterImplementation extends BasicAdapter {
  def convertItemTo[A, B](
    item : Option[A],
    adapterLogic : PartialFunction[Option[A], Option[B]]) : Option[B] = {
    val isEmpty = EmptyValidateHelper.isEmpty(
      item,
      Some(AppConstants.NoContent))

    if (isEmpty) {
      return None
    }

    adapterLogic(item)
  }

  def convertItemsTo[A, B](
    items : Option[Iterable[A]],
    adapterLogic :
    PartialFunction[Option[Iterable[A]], Option[Iterable[B]]]) :
  Option[Iterable[B]] = {
    val isEmpty = EmptyValidateHelper.isEmpty(
      items,
      Some(AppConstants.NoContent))

    if (isEmpty) {
      return None
    }

    adapterLogic(items)
  }
}
