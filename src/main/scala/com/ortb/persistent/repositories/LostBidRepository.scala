package com.ortb.persistent.repositories

import slick.jdbc.SQLiteProfile.api._
import com.ortb.manager.AppManager
import com.ortb.persistent.repositoryPattern.Repository
import com.ortb.persistent.schema.Tables
import com.ortb.persistent.schema.Tables._
import slick.dbio.Effect
import slick.sql.FixedSqlAction

class LostBidRepository(appManager : AppManager)
  extends
    Repository[Lostbid, LostbidRow, Int](appManager) {

  override def tableName : String = this.lostBidTableName

  override def getEntityId(entity : Option[Tables.LostbidRow]) : Int =
    if (entity.isDefined) entity.get.lostbidid
    else -1

  override def setEntityId(
    entityId : Option[Int],
    entity   : Option[Tables.LostbidRow]) : Option[Tables.LostbidRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(lostbidid = entityId.get))
  }

  override def getAddAction(
    entity : Tables.LostbidRow) =
    table returning table.map(_.lostbidid) into
      ((entityProjection, entityId) => entityProjection.copy(lostbidid = entityId)) += entity

  override def table = this.lostBids

  override def getDeleteAction(
    entityId : Int) : FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(
    id : Int) = table.filter(c => c.lostbidid === id)

  override def getAllQuery = for {record <- table} yield record
}
