package com.ortb.persistent.repositories

import slick.jdbc.SQLiteProfile.api._
import com.ortb.manager.AppManager
import com.ortb.persistent.repositoryPattern.Repository
import com.ortb.persistent.schema.Tables
import com.ortb.persistent.schema.Tables._
import slick.dbio.Effect
import slick.sql.FixedSqlAction

class BidResponseRepository(appManager : AppManager)
  extends
    Repository[Bidresponse, BidresponseRow, Int](appManager) {

  override def tableName : String = this.bidResponseTableName

  override def getEntityId(entity : Option[Tables.BidresponseRow]) : Int =
    if(entity.isDefined) entity.get.bidresponseid else -1

  override def setEntityId(
    entityId : Option[Int],
    entity   : Option[Tables.BidresponseRow]) : Option[Tables.BidresponseRow] = {

    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(bidresponseid = entityId.get))
  }

  override def getAddAction(
    entity : Tables.BidresponseRow) =
    table returning table.map(_.bidrequestid) into
      ((entityProjection, entityId) => entityProjection.copy(bidrequestid = entityId)) += entity

  override def table = this.bidResponses

  override def getDeleteAction(
    entityId : Int) : FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(
    id : Int) = table.filter(c => c.bidresponseid === id)

  override def getAllQuery = for {record <- table} yield record
}
