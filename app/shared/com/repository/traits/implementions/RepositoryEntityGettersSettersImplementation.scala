package shared.com.repository.traits.implementions

import shared.com.repository.traits.RepositoryEntityGettersSetters

trait RepositoryEntityGettersSettersImplementation[TTable, TRow, TKey]
  extends RepositoryEntityGettersSetters[TTable, TRow, TKey] {

  def getEntityId(entity : TRow) : TKey = {
    getEntityIdFromOptionRow(Some(entity))
  }

  def setEntityId(entityId : TKey, entity : TRow) : TRow = {
    setEntityIdFromOptionRow(Some(entityId), Some(entity)).get
  }
}
