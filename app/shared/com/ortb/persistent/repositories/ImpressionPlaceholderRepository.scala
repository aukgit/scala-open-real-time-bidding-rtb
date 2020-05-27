package shared.com.ortb.persistent.repositories

import io.circe.derivation._
import io.circe.generic.semiauto._
import io.circe._
import io.circe.generic.auto._
import com.google.inject.Inject
import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.schema.Tables
import shared.com.ortb.persistent.schema.Tables._
import shared.com.repository.RepositoryBase
import shared.io.jsonParse.implementations.JsonCirceDefaultEncodersImplementation
import slick.dbio.Effect
import slick.jdbc.SQLiteProfile.api._
import slick.sql.FixedSqlAction

class ImpressionPlaceholderRepository @Inject()(appManager: AppManager)
    extends RepositoryBase[Impressionplaceholder, ImpressionplaceholderRow, Int](appManager) {

  override def tableName: String = this.impressionPlaceholderTableName

  override def getEntityIdFromOptionRow(entity : Option[Tables.ImpressionplaceholderRow]): Int =
    if (entity.isDefined) entity.get.impressionid
    else -1

  override def setEntityIdFromOptionRow(
    entityId: Option[Int],
    entity: Option[Tables.ImpressionplaceholderRow]
  ): Option[Tables.ImpressionplaceholderRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(impressionid = entityId.get))
  }

  override def getAddAction(entity: Tables.ImpressionplaceholderRow) =
    table returning table.map(_.impressionid) into
      ((entityProjection,
        entityId) => entityProjection.copy(impressionid = entityId)) += entity

  override def table : TableQuery[Impressionplaceholder] = this.impressionPlaceholder

  override def getDeleteAction(
    entityId: Int
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(id: Int) : Query[Impressionplaceholder, ImpressionplaceholderRow, Seq] = 
    table.filter(c => c.impressionid === id)

  override def getAllQuery : Query[Impressionplaceholder, ImpressionplaceholderRow, Seq] =
    for {record <- table} yield record

  /**
   * All encoders, decoders and codec for circe
   *
   * @return
   */
  override def encoders : JsonCirceDefaultEncodersImplementation[ImpressionplaceholderRow] =
    new JsonCirceDefaultEncodersImplementation[ImpressionplaceholderRow]()
}
