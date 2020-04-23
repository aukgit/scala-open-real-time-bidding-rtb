package shared.com.ortb.model.results

import shared.com.ortb.model.attributes.GenericControllerResponseAttributesModel

case class ControllerSuccessResultsModel[TRow, TKey](
  attributes : Option[GenericControllerResponseAttributesModel] = None,
  data : Iterable[TRow])
