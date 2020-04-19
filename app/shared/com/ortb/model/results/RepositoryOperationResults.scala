package shared.com.ortb.model.results

import shared.com.ortb.enumeration.DatabaseActionType.DatabaseActionType
import shared.com.ortb.model.wrappers.persistent.EntityWrapper

case class RepositoryOperationResults[TRow, TKey](
  isSuccess : Boolean,
  entities: Iterable[EntityWrapper[TRow, TKey]],
  actionType : DatabaseActionType,
  message : String = null)
