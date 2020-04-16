package com.ortb.persistent.repositories

import com.ortb.manager.AppManager
import com.ortb.persistent.repositories.pattern.RepositoryBase
import com.ortb.persistent.schema.Tables
import com.ortb.persistent.schema.Tables._
import slick.dbio.Effect
import slick.jdbc.SQLiteProfile.api._
import slick.sql.FixedSqlAction

class KeywordRepository(appManager: AppManager)
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

  override def getAddAction(entity: Tables.KeywordRow) =
    table returning table.map(_.keywordid) into
      ((entityProjection,
        entityId) => entityProjection.copy(keywordid = entityId)) += entity

  override def table = this.keywords

  override def getDeleteAction(
    entityId: Int
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(id: Int) = table.filter(c => c.keywordid === id)

  override def getAllQuery = for { record <- table } yield record
}
