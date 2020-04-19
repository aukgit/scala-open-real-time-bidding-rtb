package shared.com.ortb.model.results

import shared.com.ortb.enumeration.DatabaseActionType.DatabaseActionType

case class RepositoryOperationResult[TRow, TKey](
  isSuccess : Boolean,
  entityId : Option[TKey],
  entity : Option[TRow],
  actionType : DatabaseActionType,
  message    : String = null)
