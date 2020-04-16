package com.ortb.persistent.repositories

import com.ortb.manager.AppManager
import com.ortb.persistent.repositories.pattern.RepositoryBase
import com.ortb.persistent.schema.Tables
import com.ortb.persistent.schema.Tables._
import slick.dbio.Effect
import slick.jdbc.SQLiteProfile.api._
import slick.sql.FixedSqlAction

class KeywordAdvertiseMappingRepository(appManager: AppManager)
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

  override def getAddAction(entity: Tables.KeywordadvertisemappingRow) =
    table returning table.map(_.keywordadvertisemappingid) into
      ((entityProjection,
        entityId) =>
         entityProjection.copy(keywordadvertisemappingid = entityId)) += entity

  override def table = this.keywordAdvertiseMappings

  override def getDeleteAction(
    entityId: Int
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(id: Int) =
    table.filter(c => c.keywordadvertisemappingid === id)

  override def getAllQuery = for { record <- table } yield record
}
