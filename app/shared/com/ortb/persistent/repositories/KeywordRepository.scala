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

class KeywordRepository @Inject()(appManager: AppManager)
    extends RepositoryBase[Keyword, KeywordRow, Int](appManager) {

  override def tableName: String = this.keywordTableName

  override def getEntityId(entity: Option[Tables.KeywordRow]): Int =
    if (entity.isDefined) entity.get.keywordid
    else -1

  override def setEntityId(
    entityId: Option[Int],
    entity: Option[Tables.KeywordRow]
  ): Option[Tables.KeywordRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(keywordid = entityId.get))
  }

  override def getAddAction(entity: Tables.KeywordRow) 
  : FixedSqlAction[KeywordRow, NoStream, Effect.Write] =
    table returning table.map(_.keywordid) into
      ((entityProjection,
        entityId) => entityProjection.copy(keywordid = entityId)) += entity

  override def table = 
    this.keywords

  override def getDeleteAction(
    entityId: Int
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(id: Int) : 
  Query[Keyword, KeywordRow, Seq] = 
    table.filter(c => c.keywordid === id)

  override def getAllQuery :
  Query[Keyword, KeywordRow, Seq] = 
    for {record <- table} yield record

  /**
   * All encoders, decoders and codec for circe
   *
   * @return
   */
  override def encoders : JsonCirceDefaultEncoders[KeywordRow] =
    new JsonCirceDefaultEncoders[KeywordRow]()
}
