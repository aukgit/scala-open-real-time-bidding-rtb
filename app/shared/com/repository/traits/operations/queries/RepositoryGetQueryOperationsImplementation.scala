package shared.com.repository.traits.operations.queries

import shared.com.repository.RepositoryBase
import shared.io.loggers.AppLogger
import slick.jdbc.SQLiteProfile.api._
import slick.lifted.Query

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
}
