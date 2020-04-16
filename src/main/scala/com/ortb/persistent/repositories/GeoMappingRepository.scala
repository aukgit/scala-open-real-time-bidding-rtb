package com.ortb.persistent.repositories

import slick.jdbc.SQLiteProfile.api._
import com.ortb.manager.AppManager
import com.ortb.persistent.repositories.pattern.RepositoryBase
import com.ortb.persistent.schema.Tables
import com.ortb.persistent.schema.Tables._
import slick.dbio.Effect
import slick.sql.FixedSqlAction

class GeoMappingRepository(appManager: AppManager)
    extends RepositoryBase[Geomapping, GeomappingRow, Int](appManager) {

  override def tableName: String = this.geoMappingTableName

  override def getEntityId(entity: Option[Tables.GeomappingRow]): Int =
    if (entity.isDefined) entity.get.geomappingid
    else -1

  override def setEntityId(
    entityId: Option[Int],
    entity: Option[Tables.GeomappingRow]
  ): Option[Tables.GeomappingRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(geomappingid = entityId.get))
  }

  override def getAddAction(entity: Tables.GeomappingRow) =
    table returning table.map(_.geomappingid) into
      ((entityProjection,
        entityId) => entityProjection.copy(geomappingid = entityId)) += entity

  override def table = this.geoMappings

  override def getDeleteAction(
    entityId: Int
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(id: Int) = table.filter(c => c.geomappingid === id)

  override def getAllQuery = for { record <- table } yield record
}
