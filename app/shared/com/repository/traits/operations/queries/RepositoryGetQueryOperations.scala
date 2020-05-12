package shared.com.repository.traits.operations.queries

import shared.com.repository.traits.operations.mutations.RepositoryOperationsBase
import slick.dbio.Effect
import slick.lifted.Query
import slick.sql.FixedSqlStreamingAction

trait RepositoryGetQueryOperations[TTable, TRow, TKey]
  extends RepositoryOperationsBase[TRow] {
  def getTop500RowsFromQuery(
    query : Query[TTable, TRow, Seq]) : Option[Seq[TRow]]

  def getRowsFromQuery(
    query : Query[TTable, TRow, Seq],
    limit : Int = -1) : Option[Seq[TRow]]

  /**
   * Get Results from queryResult
   * @param queryResult : Pass query.Result
   * @tparam TRow2 : Get the query rows
   * @return
   */
  def getResults[TRow2](
    queryResult : FixedSqlStreamingAction[Seq[TRow2], TRow2, Effect.Read]) : Seq[TRow2]
}
