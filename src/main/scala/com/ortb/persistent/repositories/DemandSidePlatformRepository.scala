package com.ortb.persistent.repositories

import slick.jdbc.SQLiteProfile.api._
import com.ortb.manager.AppManager
import com.ortb.persistent.repositoryPattern.Repository
import com.ortb.persistent.schema.Tables
import com.ortb.persistent.schema.Tables._
import slick.dbio.Effect
import slick.sql.FixedSqlAction

class DemandSidePlatformRepository(appManager : AppManager)
  extends
    Repository[Demandsideplatform, DemandsideplatformRow, Int](appManager) {

  override def tableName : String = this.demandSidePlatformTableName

  override def getEntityId(entity : Option[Tables.DemandsideplatformRow]) : Int =
    if (entity.isDefined) entity.get.demandsideplatformid
    else -1

  override def setEntityId(
    entityId : Option[Int],
    entity   : Option[Tables.DemandsideplatformRow]) : Option[Tables.DemandsideplatformRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(demandsideplatformid = entityId.get))
  }

  override def getAddAction(
    entity : Tables.DemandsideplatformRow) =
    table returning table.map(_.demandsideplatformid) into
      ((entityProjection, entityId) => entityProjection.copy(demandsideplatformid = entityId)) += entity

  override def table = this.demandSidePlatforms

  override def getDeleteAction(
    entityId : Int) : FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(
    id : Int) = table.filter(c => c.demandsideplatformid === id)

  override def getAllQuery = for {record <- table} yield record
}
