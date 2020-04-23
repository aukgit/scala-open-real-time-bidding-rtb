package shared.com.ortb.model.results

import shared.com.ortb.model.attributes.GenericResponseAttributesModel
import shared.com.ortb.model.wrappers.persistent.EntityWrapper

case class RepositoryOperationResultsModel[TRow, TKey](
  attributes : Option[GenericResponseAttributesModel],
  data : List[EntityWrapper[TRow, TKey]])
