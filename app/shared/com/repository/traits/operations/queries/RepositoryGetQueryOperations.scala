package shared.com.repository.traits.operations.queries

import shared.com.repository.traits.operations.mutations.RepositoryOperationsBase
import slick.lifted.Query

trait RepositoryGetQueryOperations[TTable, TRow, TKey]
  extends RepositoryOperationsBase[TRow] {
  def getTop500RowsFromQuery(
    query : Query[TTable, TRow, Seq]) : Option[Seq[TRow]]

  def getRowsFromQuery(
    query : Query[TTable, TRow, Seq],
    limit : Int = -1) : Option[Seq[TRow]]
}
