package shared.com.ortb.model.repository.response

import shared.com.ortb.model.repository.ApiPaginationResponseAttributesModel

case class ApiPaginationResultsModel[TRow, TKey](
  attributes : Option[ApiPaginationResponseAttributesModel] = None,
  data : Iterable[TRow])

