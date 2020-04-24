package shared.com.repository.traits.operations.queries

import shared.com.repository.traits.operations.mutations.RepositoryOperationsBase
import slick.lifted.Query

trait RepositoryGetByIdQueryOperations[TTable, TRow, TKey]
  extends RepositoryOperationsBase[TRow] {

  def getById(entityId : TKey) : Option[TRow]

  def getQueryByIdSingle(id : TKey) : Query[TTable, TRow, Seq]

  def getQueryById(id : TKey) : Query[TTable, TRow, Seq]

  def isExists(entityId : TKey) : Boolean
}
