package shared.com.ortb.model.results

import shared.com.ortb.model.attributes.ApiPaginationResponseAttributesModel

case class ApiPaginationResultsModel[TRow, TKey](
  attributes : Option[ApiPaginationResponseAttributesModel] = None,
  data : Iterable[TRow])
