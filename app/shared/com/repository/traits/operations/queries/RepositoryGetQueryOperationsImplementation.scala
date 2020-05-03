package shared.com.repository.traits.operations.queries

import shared.com.repository.RepositoryBase
import shared.io.loggers.AppLogger
import slick.dbio.Effect
import slick.jdbc.SQLiteProfile.api._
import slick.lifted.Query
import slick.sql.FixedSqlStreamingAction

trait RepositoryGetQueryOperationsImplementation[TTable, TRow, TKey]
  extends RepositoryGetQueryOperations[TTable, TRow, TKey] {
  this : RepositoryBase[TTable, TRow, TKey] =>

  def getTop500RowsFromQuery(
    query : Query[TTable, TRow, Seq]) : Option[Seq[TRow]] = {
    getRowsFromQuery(query, 500)
  }

  def getRowsFromQuery(
    query : Query[TTable, TRow, Seq],
    limit : Int = -1) : Option[Seq[TRow]] = {
    try {
      if(limit > 0){
        return Some(this.run(query.take(limit).result))
      }

      return Some(this.run(query.result))
    } catch {
      case e : Exception => AppLogger.errorCaptureAndThrow(e)
    }

    None
  }

  /**
   * Get Results from queryResult
   * @param queryResult : Pass query.Result
   * @tparam TRow2 : Get the query rows
   * @return
   */
  def getResults[TRow2](
    queryResult : FixedSqlStreamingAction[Seq[TRow2], TRow2, Effect.Read]) : Seq[TRow2] = {
    try {
      return this.runAs(queryResult)
    } catch {
      case e : Exception => AppLogger.errorCaptureAndThrow(e)
    }

    null
  }
}
