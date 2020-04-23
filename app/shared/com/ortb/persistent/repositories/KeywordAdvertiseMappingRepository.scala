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
import shared.io.jsonParse.JsonCirceDefaultEncoders
import slick.dbio.Effect
import slick.sql.FixedSqlAction

class KeywordAdvertiseMappingRepository @Inject()(appManager: AppManager)
    extends RepositoryBase[
      Keywordadvertisemapping,
      KeywordadvertisemappingRow,
      Int
    ](appManager) {

  override def tableName: String = this.keywordAdvertiseMappingTableName

  override def getEntityId(
    entity: Option[Tables.KeywordadvertisemappingRow]
  ): Int =
    if (entity.isDefined) entity.get.keywordadvertisemappingid
    else -1

  override def setEntityId(
    entityId: Option[Int],
    entity: Option[Tables.KeywordadvertisemappingRow]
  ): Option[Tables.KeywordadvertisemappingRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(keywordadvertisemappingid = entityId.get))
  }

  override def getAddAction(entity: Tables.KeywordadvertisemappingRow) :
  FixedSqlAction[KeywordadvertisemappingRow, NoStream, Effect.Write] =
    table returning table.map(_.keywordadvertisemappingid) into
      ((entityProjection,
        entityId) =>
         entityProjection.copy(keywordadvertisemappingid = entityId)) += entity

  override def table : TableQuery[Keywordadvertisemapping] = 
    this.keywordAdvertiseMappings

  override def getDeleteAction(
    entityId: Int
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(id: Int) : Query[Keywordadvertisemapping, schema.Tables
  .KeywordadvertisemappingRow, Seq] =
    table.filter(c => c.keywordadvertisemappingid === id)

  override def getAllQuery =
    for { record <- table } yield record

  /**
   * All encoders, decoders and codec for circe
   *
   * @return
   */
  override def encoders : JsonCirceDefaultEncoders[KeywordadvertisemappingRow] =
    new JsonCirceDefaultEncoders[KeywordadvertisemappingRow]()
}
