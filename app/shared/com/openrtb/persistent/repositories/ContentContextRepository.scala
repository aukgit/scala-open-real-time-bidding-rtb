package com.ortb.persistent.repositories

import com.ortb.manager.AppManager
import com.ortb.persistent.repositories.pattern.RepositoryBase
import com.ortb.persistent.schema.Tables
import com.ortb.persistent.schema.Tables._
import slick.dbio.Effect
import slick.jdbc.SQLiteProfile.api._
import slick.sql.FixedSqlAction

class ContentContextRepository(appManager: AppManager)
    extends RepositoryBase[Contentcontext, ContentcontextRow, Int](appManager) {

  override def tableName: String = this.contentContextTableName

  override def getEntityId(entity: Option[Tables.ContentcontextRow]): Int =
    if (entity.isDefined) entity.get.contentcontextid
    else -1

  override def setEntityId(
    entityId: Option[Int],
    entity: Option[Tables.ContentcontextRow]
  ): Option[Tables.ContentcontextRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(contentcontextid = entityId.get))
  }

  override def getAddAction(entity: Tables.ContentcontextRow) =
    table returning table.map(_.contentcontextid) into
      ((entityProjection,
        entityId) =>
         entityProjection.copy(contentcontextid = entityId)) += entity

  override def table = this.contentContexts

  override def getDeleteAction(
    entityId: Int
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(id: Int) =
    table.filter(c => c.contentcontextid === id)

  override def getAllQuery = for { record <- table } yield record
}
