package shared.com.repository.traits.operations.queries

import shared.com.ortb.model.results.RepositoryOperationResultsModel
import shared.com.ortb.model.wrappers.PaginationWrapperModel
import shared.com.repository.traits.operations.mutations.RepositoryOperationsBase
import slick.dbio.Effect
import slick.lifted.Query
import slick.sql.FixedSqlStreamingAction

trait RepositoryGetPagedQueryOperations[TTable, TRow, TKey]
  extends RepositoryOperationsBase[TRow] {
  /**
   *
   * @param query : to be executed and get the rows from.
   * @param limit : -1 take all no limit
   * @param message : message to be added with the response.
   *
   * @return
   */
  def takeFromAnyQueryAsResponse(
    query : Query[TTable, TRow, Seq],
    limit : Int = 100,
    message : String = null) : RepositoryOperationResultsModel[TRow,TKey]

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
