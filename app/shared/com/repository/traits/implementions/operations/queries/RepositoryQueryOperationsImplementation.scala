package shared.com.repository.traits.implementions.operations.queries

import shared.com.repository.RepositoryBase
import shared.com.repository.traits.operations.queries._
import shared.io.loggers.AppLogger
import slick.dbio.Effect
import slick.jdbc.SQLiteProfile
import slick.jdbc.SQLiteProfile.api._
import slick.sql.FixedSqlAction

import scala.concurrent.Future

trait RepositoryQueryOperationsImplementation[TTable, TRow, TKey]
  extends RepositoryQueryOperations[TTable, TRow, TKey]
    with RepositoryGetAllQueryOperationsImplementation[TTable, TRow, TKey]
    with RepositoryGetByIdQueryOperationsImplementation[TTable, TRow, TKey]
    with RepositorySingleQueryOperationsImplementation[TTable, TRow, TKey]
    with RepositoryGetPagedQueryOperationsImplementation[TTable, TRow, TKey] {
  this : RepositoryBase[TTable, TRow, TKey] =>

  def count : Option[Int] = {
    try {
      val count : Future[Int] = this.db.run(table.length.result)

      return Some(toRegular(count, defaultTimeout))
    } catch {
      case e : Exception => AppLogger.errorCaptureAndThrow(e)
    }

    None
  }

  def count(query : FixedSqlAction[Int, SQLiteProfile.api.NoStream, Effect.Read]) : Option[Int] = {
    try {
      val count : Future[Int] = this.db.run(query)

      return Some(toRegular(count, defaultTimeout))
    } catch {
      case e : Exception => AppLogger.errorCaptureAndThrow(e)
    }

    None
  }
}
