package shared.com.ortb.persistent.repositories

import com.google.inject.Inject
import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.schema.Tables
import shared.com.ortb.persistent.schema.Tables._
import shared.com.repository.RepositoryBase
import shared.io.jsonParse.implementations.JsonCirceDefaultEncodersImplementation
import slick.dbio.Effect
import slick.jdbc.SQLiteProfile.api._
import slick.sql.FixedSqlAction

class LogTraceRepository @Inject()(appManager: AppManager)
    extends RepositoryBase[Logtrace, LogtraceRow, Int](appManager) {

  override def tableName: String = this.logTraceTableName

  override def getEntityIdFromOptionRow(entity : Option[Tables.LogtraceRow]): Int =
    if (entity.isDefined) entity.get.logtraceid
    else -1

  override def setEntityIdFromOptionRow(
      entityId: Option[Int],
      entity: Option[Tables.LogtraceRow]
  ): Option[Tables.LogtraceRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(logtraceid = entityId.get))
  }

  override def getAddAction(entity: Tables.LogtraceRow)
    : FixedSqlAction[LogtraceRow, NoStream, Effect.Write] =
    table returning table.map(_.logtraceid) into
      ((entityProjection,
        entityId) => entityProjection.copy(logtraceid = entityId)) += entity

  override def table: TableQuery[Logtrace] =
    this.logTraces

  override def getDeleteAction(
      entityId: Int
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(id: Int): Query[Logtrace, LogtraceRow, Seq] =
    table.filter(c => c.logtraceid === id)

  override def getAllQuery: Query[Logtrace, LogtraceRow, Seq] =
    for { record <- table } yield record

  /**
    * All encoders, decoders and codec for circe
    *
    * @return
    */
  override def encoders: JsonCirceDefaultEncodersImplementation[LogtraceRow] =
    new JsonCirceDefaultEncodersImplementation[LogtraceRow]()
}
