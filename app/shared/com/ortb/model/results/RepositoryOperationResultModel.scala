package shared.com.ortb.model.results

import shared.com.ortb.model.attributes.GenericResponseAttributesModel
import shared.com.ortb.model.wrappers.persistent.EntityWrapper

case class RepositoryOperationResultModel[TRow, TKey](
  attributes : Option[GenericResponseAttributesModel],
  data : Option[EntityWrapper[TRow, TKey]] = None)
