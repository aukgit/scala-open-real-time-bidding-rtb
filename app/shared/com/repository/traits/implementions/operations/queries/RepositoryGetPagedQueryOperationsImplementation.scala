package shared.com.repository.traits.implementions.operations.queries

import shared.com.ortb.model.wrappers.PaginationWrapperModel
import slick.jdbc.SQLiteProfile.api._
import shared.com.repository.RepositoryBase
import shared.com.repository.traits.operations.queries.RepositoryGetPagedQueryOperations
import shared.io.loggers.AppLogger
import slick.dbio.Effect
import slick.lifted.Query
import slick.sql.FixedSqlStreamingAction

trait RepositoryGetPagedQueryOperationsImplementation[TTable, TRow, TKey]
  extends RepositoryGetPagedQueryOperations[TRow] {
  this : RepositoryBase[TTable, TRow, TKey] =>

  def take[TRow2](
    query : FixedSqlStreamingAction[Seq[TRow2], TRow2, Effect.Read]) : Seq[TRow2] = {
    try {
      this.runAs(query)
    } catch {
      case e : Exception => AppLogger.error(e)
    }

    null
  }

  def currentTableLimit(
    limit : Int) : Seq[TRow] = {
    val x : FixedSqlStreamingAction[Seq[TRow], TRow, Effect.Read] = getAllQuery.result
    take[TRow](getAllQuery.take(limit).result)
  }

  def getCurrentTablePaged(
    pageIndex : Int = 1,
    eachPageItems : Int) : Seq[TRow] = {
    getPaged[TTable, TRow](getAllQuery, pageIndex, eachPageItems)
  }

  def getCurrentTablePaged(
    paginationWrapperModel : PaginationWrapperModel) : Seq[TRow] = {
    getCurrentTablePaged(
      paginationWrapperModel.page,
      paginationWrapperModel.pageSize)
  }

  def getPaged[TTable2, TRow2](
    rows : Query[TTable2, TRow2, Seq],
    paginationWrapperModel : PaginationWrapperModel) : Seq[TRow2] = {
    getPaged[TTable2, TRow2](
      rows,
      paginationWrapperModel.page,
      paginationWrapperModel.pageSize)
  }

  def getPaged[TTable2, TRow2](
    rows  : Query[TTable2, TRow2, Seq],
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
        "Skipping number cannot be negative. Page Index(>=1) or eachPageItems(>0) has negative sign.")
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
