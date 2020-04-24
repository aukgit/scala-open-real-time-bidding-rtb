package shared.com.repository.traits

import shared.com.repository.traits.operations.mutations.RepositoryOperationsBase

trait RepositoryEntityGettersSetters[TTable, TRow, TKey]
  extends RepositoryOperationsBase[TRow] {

  def getEntityIdFromOptionRow(entity : Option[TRow]) : TKey

  def setEntityIdFromOptionRow(entityId : Option[TKey], entity : Option[TRow]) : Option[TRow]

  def getEntityId(entity : TRow) : TKey

  def setEntityId(entityId : TKey, entity : TRow) : TRow
}