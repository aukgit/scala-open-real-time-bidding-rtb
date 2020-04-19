package shared.com.ortb.model.repository.response

import shared.com.ortb.enumeration.DatabaseActionType.DatabaseActionType
import shared.com.ortb.model.repository.GenericResponseAttributesModel
import shared.com.ortb.model.wrappers.persistent.EntityWrapper



case class RepositoryOperationResultsModel[TRow, TKey](
  attributes : GenericResponseAttributesModel,
  data: List[EntityWrapper[TRow, TKey]])
