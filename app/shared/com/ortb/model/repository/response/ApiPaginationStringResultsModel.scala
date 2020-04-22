package shared.com.ortb.model.repository.response

import shared.com.ortb.model.repository.ApiPaginationResponseAttributesModel

case class ApiPaginationStringResultsModel[TRow](
  attributes : ApiPaginationResponseAttributesModel,
  data : List[TRow])
