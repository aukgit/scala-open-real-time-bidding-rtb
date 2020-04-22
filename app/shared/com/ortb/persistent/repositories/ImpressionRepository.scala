package shared.com.ortb.persistent.repositories

import io.circe.generic.semiauto._
import io.circe._
import io.circe.generic.auto._
import com.google.inject.Inject
import slick.jdbc.SQLiteProfile.api._
import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.schema
import shared.com.ortb.persistent.schema.Tables
import shared.com.ortb.persistent.schema.Tables._
import shared.com.repository.RepositoryBase
import shared.io.traits.jsonParse.JsonCirceDefaultEncoders
import slick.dbio.Effect
import slick.sql.FixedSqlAction

class ImpressionRepository @Inject()(appManager: AppManager)
    extends RepositoryBase[Impression, ImpressionRow, Int](appManager) {

  override def tableName: String = this.impressionTableName

  override def getEntityId(entity: Option[Tables.ImpressionRow]): Int =
    if (entity.isDefined) entity.get.impressionid
    else -1

  override def setEntityId(
    entityId: Option[Int],
    entity: Option[Tables.ImpressionRow]
  ): Option[Tables.ImpressionRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(impressionid = entityId.get))
  }

  override def getAddAction(entity: Tables.ImpressionRow) : FixedSqlAction[ImpressionRow, NoStream, Effect.Write] =
    table returning table.map(_.impressionid) into
      ((entityProjection,
        entityId) => entityProjection.copy(impressionid = entityId)) += entity

  override def table : TableQuery[Impression] = this.impressions

  override def getDeleteAction(
    entityId: Int
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(id: Int) : Query[Impression, ImpressionRow, Seq] = 
    table.filter(c => c.impressionid === id)

  override def getAllQuery : Query[Impression, ImpressionRow, Seq] =
    for {record <- table} yield record

  /**
   * All encoders, decoders and codec for circe
   *
   * @return
   */
  override def encoders : JsonCirceDefaultEncoders[ImpressionRow] = new JsonCirceDefaultEncoders[ImpressionRow]()
}
