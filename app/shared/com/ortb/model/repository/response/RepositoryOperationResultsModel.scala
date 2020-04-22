package shared.com.ortb.model.repository.response

import shared.com.ortb.model.repository.{ ApiPaginationResponseAttributesModel, GenericResponseAttributesModel }
import shared.com.ortb.model.wrappers.persistent.EntityWrapper

case class RepositoryOperationResultsModel[TRow, TKey](
  attributes : Option[GenericResponseAttributesModel],
  data : List[EntityWrapper[TRow, TKey]])

