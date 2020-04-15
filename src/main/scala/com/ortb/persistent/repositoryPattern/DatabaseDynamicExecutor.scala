package com.ortb.persistent.repositoryPattern

import scala.concurrent.Future
import com.ortb.persistent.schema.DatabaseSchema
import slick.dbio.{NoStream, Effect}
import com.ortb.manager.AppManager
import slick.sql.{FixedSqlAction, FixedSqlStreamingAction, SqlStreamingAction}
import java.awt.dnd.InvalidDnDOperationException

import com.ortb.enumeration.DatabaseActionType.DatabaseActionType

class DatabaseDynamicExecutor[TTable, TRow, TKey](appManager : AppManager)
  extends DatabaseSchema(appManager : AppManager) {

  def getRunResult[T >: Null <: AnyRef](
    dbAction           : T,
    databaseActionType : DatabaseActionType
  ) : Option[Future[Seq[TRow]]] = {
    dbAction match {
      case fixedSql : FixedSqlAction[Seq[TRow], _, _]
      =>
        Some(db.run(fixedSql))
      case fixedSqlStreaming : FixedSqlStreamingAction[Seq[TRow], NoStream, Effect.All] =>
        Some(db.run(fixedSqlStreaming))
      case sqlStreaming : SqlStreamingAction[Seq[TRow], NoStream, Effect.All] =>
        Some(db.run(sqlStreaming))
      case _ =>
        throw new InvalidDnDOperationException(s"Invalid operation for runAsync. Operation $dbAction")
    }
  }
}
