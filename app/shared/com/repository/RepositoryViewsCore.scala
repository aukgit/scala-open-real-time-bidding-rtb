package shared.com.repository

import com.google.inject.Inject
import shared.com.ortb.exceptions.NotSupportException
import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.schema.Tables
import slick.dbio.{ Effect, NoStream }
import slick.lifted.Query
import slick.sql.FixedSqlAction

abstract class RepositoryViewsCore[TTable, TRow] @Inject()
(appManager : AppManager)
  extends RepositoryBase[TTable, TRow, Int](appManager) {
  lazy private val notSupportedMessage = "is not supported for views"

  @deprecated(s"getAddAction $notSupportedMessage")
  final override def getAddAction(entity : TRow) :
  FixedSqlAction[TRow, NoStream, Effect.Write] =
    throw new NotSupportException(s"getAddAction $notSupportedMessage")

  @deprecated(s"getEntityIdFromOptionRow $notSupportedMessage")
  final override def getEntityIdFromOptionRow(entity : Option[TRow]) : Int =
    throw new NotSupportException(s"getEntityIdFromOptionRow $notSupportedMessage")

  @deprecated(s"setEntityIdFromOptionRow $notSupportedMessage")
  final override def setEntityIdFromOptionRow
    (entityId : Option[Int], entity : Option[TRow]) : Option[TRow] =
    throw new NotSupportException(s"setEntityIdFromOptionRow $notSupportedMessage")

  @deprecated(s"getQueryById $notSupportedMessage")
  final override def getQueryById(id : Int) : Query[TTable, TRow, Seq] =
    throw new NotSupportException(s"getQueryById $notSupportedMessage")

  @deprecated(s"getDeleteAction $notSupportedMessage")
  final override def getDeleteAction(entityId : Int) :
  FixedSqlAction[Int, NoStream, Effect.Write] =
    throw new NotSupportException(s"getDeleteAction $notSupportedMessage")
}
