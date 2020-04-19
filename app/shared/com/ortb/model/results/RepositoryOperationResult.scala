package shared.com.ortb.model.results

import shared.com.ortb.enumeration.DatabaseActionType.DatabaseActionType
import shared.com.ortb.model.wrappers.persistent.EntityWrapper
import slick.dbio.DatabaseAction

case class RepositoryOperationResult[TRow, TKey](
  isSuccess : Boolean, entityId : Option[TKey], entity : Option[TRow], actionType : DatabaseActionType, message : String = null)
