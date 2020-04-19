package shared.com.ortb.model.repository.response

import shared.com.ortb.model.repository.GenericResponseAttributesModel
import shared.com.ortb.model.wrappers.persistent.EntityWrapper

case class RepositoryOperationResultModel[TRow, TKey](
  attributes : GenericResponseAttributesModel,
  data : EntityWrapper[TRow, TKey])
