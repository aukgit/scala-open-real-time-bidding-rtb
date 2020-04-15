package com.ortb.persistent.repositories

import slick.jdbc.SQLiteProfile.api._
import com.ortb.manager.AppManager
import com.ortb.persistent.repositoryPattern.RepositoryBase
import com.ortb.persistent.schema
import com.ortb.persistent.schema.Tables
import com.ortb.persistent.schema.Tables._
import slick.dbio.Effect
import slick.lifted.Query
import slick.sql.FixedSqlAction

class BidRequestRepository(appManager : AppManager)
  extends
    RepositoryBase[Bidrequest, BidrequestRow, Int](appManager) {

  override def tableName : String = this.bidRequestTableName

  override def getEntityId(entity : Option[Tables.BidrequestRow]) : Int =
    if(entity.isDefined) entity.get.bidrequestid else -1

  override def setEntityId(
    entityId : Option[Int],
    entity   : Option[Tables.BidrequestRow]) : Option[Tables.BidrequestRow] = {

    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(bidrequestid = entityId.get))
  }

  override def getAddAction(
    entity : Tables.BidrequestRow) =
    table returning table.map(_.bidrequestid) into
      ((entityProjection, entityId) => entityProjection.copy(bidrequestid = entityId)) += entity

  override def table = this.bidRequests

  override def getDeleteAction(
    entityId : Int) : FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(
    id : Int) = table.filter(c => c.bidrequestid === id)

  override def getAllQuery = for {record <- table} yield record
}
