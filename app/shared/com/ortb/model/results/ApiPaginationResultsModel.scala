package shared.com.ortb.model.results

import shared.com.ortb.model.attributes.{ ApiPaginationResponseAttributesModel, GenericControllerResponseAttributesModel }

case class ApiPaginationResultsModel[TRow, TKey](
  attributes : Option[ApiPaginationResponseAttributesModel] = None,
  data : Iterable[TRow])
