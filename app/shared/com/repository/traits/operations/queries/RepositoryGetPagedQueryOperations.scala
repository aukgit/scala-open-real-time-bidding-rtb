package shared.com.repository.traits.operations.queries

import shared.com.ortb.model.wrappers.PaginationWrapperModel
import shared.com.repository.traits.operations.mutations.RepositoryOperationsBase
import slick.dbio.Effect
import slick.lifted.Query
import slick.sql.FixedSqlStreamingAction

trait RepositoryGetPagedQueryOperations[TRow]
  extends RepositoryOperationsBase[TRow] {
  def take[TRow2](
    query : FixedSqlStreamingAction[Seq[TRow2], TRow2, Effect.Read]) : Seq[TRow2]

  /**
   *
   * @param query
   * @param limit : -1 take all no limit
   * @tparam TTable2
   * @tparam TRow2
   *
   * @return
   */
  def takeFromAnyQuery[TTable2, TRow2](
    query : Query[TTable2, TRow2, Seq],
    limit : Int = 100) : Seq[TRow2]

  def takeCurrentTableRows(
    limit : Int) : Seq[TRow]

  def getCurrentTablePaged(
    pageIndex : Int = 1,
    eachPageItems : Int) : Seq[TRow]

  def getCurrentTablePaged(
    paginationWrapperModel : PaginationWrapperModel) : Seq[TRow]

  def getPaged[TTable2, TRow2](
    rows : Query[TTable2, TRow2, Seq],
    paginationWrapperModel : PaginationWrapperModel) : Seq[TRow2]

  def getPaged[TTable2, TRow2](
    rows : Query[TTable2, TRow2, Seq],
    pageIndex : Int = 1,
    eachPageItems : Int) : Seq[TRow2]
}
