package shared.com.repository.traits.operations.query

import shared.com.repository.traits.operations.mutations.RepositoryOperationsBase

import scala.concurrent.Future

trait RepositorySingleQueryOperations[TTable, TRow, TKey]
  extends RepositoryOperationsBase[TRow] {

  def isEmptyGivenEntity(
    entityId : Option[TKey],
    entity               : Option[TRow]) : Boolean

  def getFirstOrDefault(rows : Future[Seq[TRow]]) : Option[TRow]

  def getFirstOrDefault(rows : Seq[TRow]) : Option[TRow]
}
