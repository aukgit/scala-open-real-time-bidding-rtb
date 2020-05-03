package shared.com.repository.traits.implementions.operations.queries

import shared.com.ortb.model.wrappers.PaginationWrapperModel
import shared.com.repository.RepositoryBase
import shared.com.repository.traits.operations.queries.RepositoryGetPagedQueryOperations
import shared.io.loggers.AppLogger
import slick.dbio.Effect
import slick.jdbc.SQLiteProfile.api._
import slick.lifted.Query
import slick.sql.FixedSqlStreamingAction

trait RepositoryGetPagedQueryOperationsImplementation[TTable, TRow, TKey]
  extends RepositoryGetPagedQueryOperations[TRow] {
  this : RepositoryBase[TTable, TRow, TKey] =>

  def take[TRow2](
    queryResult : FixedSqlStreamingAction[Seq[TRow2], TRow2, Effect.Read]) : Seq[TRow2] = {
    try {
      return this.runAs(queryResult)
    } catch {
      case e : Exception => AppLogger.errorCaptureAndThrow(e)
    }

    null
  }

  /**
   *
   * @param query : to be executed and get the rows from.
   * @param limit : -1 take all no limit
   * @tparam TTable2
   * @tparam TRow2
   *
   * @return
   */
  def takeFromAnyQuery[TTable2, TRow2](
    query : Query[TTable2, TRow2, Seq],
    limit : Int = 100) : Seq[TRow2] = {
    try {
      if (limit > 0) {
        return this.runAs(query.take(limit).result)
      }

      return this.runAs(query.result)
    } catch {
      case e : Exception => AppLogger.errorCaptureAndThrow(e)
    }

    null
  }

  def takeCurrentTableRows(
    limit : Int) : Seq[TRow] =
    take[TRow](getAllQuery.take(limit).result)

  def getCurrentTablePaged(
    pageIndex : Int = 1,
    eachPageItems : Int) : Seq[TRow] =
    getPaged[TTable, TRow](getAllQuery, pageIndex, eachPageItems)

  def getCurrentTablePaged(
    paginationWrapperModel : PaginationWrapperModel) : Seq[TRow] =
    getCurrentTablePaged(
      paginationWrapperModel.page,
      paginationWrapperModel.pageSize)

  def getPaged[TTable2, TRow2](
    rowsQuery : Query[TTable2, TRow2, Seq],
    paginationWrapperModel : PaginationWrapperModel) : Seq[TRow2] = {
    getPaged[TTable2, TRow2](
      rowsQuery,
      paginationWrapperModel.page,
      paginationWrapperModel.pageSize)
  }

  def getPaged[TTable2, TRow2](
    rows : Query[TTable2, TRow2, Seq],
    pageIndex : Int = 1,
    eachPageItems : Int) : Seq[TRow2] = {
    /**
     * eachPageItems = 10
     * pageIndex = 4
     * skipItems = 10 * (4 - 1) = 30
     */
    val skipItems = eachPageItems * (pageIndex - 1) //

    if (skipItems < 0) {
      throw new Exception(
        "Skipping number cannot be negative. Page Index(Should be >= 1) or eachPageItems(Should be > 0) has negative sign.")
    }

    try {
      var query : Query[TTable2, TRow2, Seq] = null

      if (skipItems == 0) {
        query = rows.take(eachPageItems)
      } else {
        query = rows.drop(skipItems).take(eachPageItems)
      }

      return this.runAs(query.result)
    } catch {
      case e : Exception => AppLogger.errorCaptureAndThrow(e)
    }

    null
  }
}
