package com.ortb.persistent.repositories

import slick.jdbc.SQLiteProfile.api._
import com.ortb.manager.AppManager
import com.ortb.persistent.repositories.pattern.RepositoryBase
import com.ortb.persistent.schema.Tables
import com.ortb.persistent.schema.Tables._
import slick.dbio.Effect
import slick.lifted.Query
import slick.sql.FixedSqlAction

class AdvertiseRepository(appManager: AppManager)
    extends RepositoryBase[Advertise, AdvertiseRow, Int](appManager) {

  override def tableName: String = this.advertiseTableName

  override def getEntityId(entity: Option[Tables.AdvertiseRow]): Int =
    entity.getOrElse(-1).asInstanceOf[Int]

  override def setEntityId(
    entityId: Option[Int],
    entity: Option[Tables.AdvertiseRow]
  ): Option[Tables.AdvertiseRow] = {

    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(advertiseid = entityId.get))
  }

  override def getAddAction(
    entity: Tables.AdvertiseRow
  ): FixedSqlAction[Tables.AdvertiseRow, NoStream, Effect.Write] =
    table returning table.map(_.advertiseid) into
      ((entityProjection,
        entityId) => entityProjection.copy(campaignid = entityId)) += entity

  override def table = this.advertises

  override def getDeleteAction(
    entityId: Int
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(
    id: Int
  ): Query[Tables.Advertise, Tables.AdvertiseRow, Seq] =
    table.filter(c => c.advertiseid === id)

  override def getAllQuery = for { record <- table } yield record
}
