package shared.com.repository.traits.operations.query

import shared.com.repository.traits.operations.mutations.RepositoryOperationsBase

trait RepositoryEntityGettersSettersOperations[TTable, TRow, TKey]
  extends RepositoryOperationsBase[TRow] {

  def getEntityId(entity : Option[TRow]) : TKey

  def setEntityId(entityId : Option[TKey], entity : Option[TRow]) : Option[TRow]
}
