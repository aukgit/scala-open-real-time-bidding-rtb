package com.ortb.persistent.repositories

import slick.jdbc.SQLiteProfile.api._
import com.ortb.manager.AppManager
import com.ortb.persistent.repositories.pattern.RepositoryBase
import com.ortb.persistent.schema.Tables
import com.ortb.persistent.schema.Tables._
import slick.dbio.Effect
import slick.sql.FixedSqlAction

/**
  * Also known as for seats
  * @param appManager
  */
class PublisherRepository(appManager: AppManager)
    extends RepositoryBase[Publisher, PublisherRow, Int](appManager) {

  override def tableName: String = this.publisherTableName

  override def getEntityId(entity: Option[Tables.PublisherRow]): Int =
    if (entity.isDefined) entity.get.publisherid
    else -1

  override def setEntityId(
    entityId: Option[Int],
    entity: Option[Tables.PublisherRow]
  ): Option[Tables.PublisherRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(publisherid = entityId.get))
  }

  override def getAddAction(entity: Tables.PublisherRow) =
    table returning table.map(_.publisherid) into
      ((entityProjection,
        entityId) => entityProjection.copy(publisherid = entityId)) += entity

  override def table = this.publishers

  override def getDeleteAction(
    entityId: Int
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(id: Int) = table.filter(c => c.publisherid === id)

  override def getAllQuery = for { record <- table } yield record
}
