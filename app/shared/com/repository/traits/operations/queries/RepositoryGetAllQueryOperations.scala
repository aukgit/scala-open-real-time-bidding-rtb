package shared.com.repository.traits.operations.queries

import shared.com.repository.traits.operations.mutations.RepositoryOperationsBase
import slick.lifted.Query

import scala.concurrent.Future

trait RepositoryGetAllQueryOperations[TTable, TRow, TKey]
  extends RepositoryOperationsBase[TRow] {
  def getAllAsList : List[TRow]

  def getAll : Seq[TRow]

  def getAllAsync : Future[Seq[TRow]]

  def getAllQuery : Query[TTable, TRow, Seq]
}


