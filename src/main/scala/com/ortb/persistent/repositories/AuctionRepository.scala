package com.ortb.persistent.repositories

import slick.jdbc.SQLiteProfile.api._
import com.ortb.manager.AppManager
import com.ortb.persistent.repositoryPattern.Repository
import com.ortb.persistent.schema.Tables._
import slick.dbio.Effect
import slick.lifted.Query
import slick.sql.FixedSqlAction

class AuctionRepository(appManager : AppManager)
  extends
    Repository[Auction, AuctionRow, Int](appManager) {

  override def tableName : String = this.auctionTableName

  override def getEntityId(
    entity : Option[AuctionRow]) : Int =
    if(entity.isDefined) entity.get.auctionid else -1

  override def setEntityId(
    entityId : Option[Int],
    entity : Option[AuctionRow]) : Option[AuctionRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(auctionid = entityId.get))
  }

  override def getAddAction(
    entity : AuctionRow) : FixedSqlAction[AuctionRow, NoStream, Effect.Write] = {
    table returning table.map(_.auctionid) into
      ((entityProjection, entityId) => entityProjection.copy(auctionid = entityId)) += entity
  }

  override def getDeleteAction(
    entityId : Int) : FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(
    id : Int) : Query[Auction, AuctionRow, Seq] =
    table.filter(c => c.advertiseid === id)

  override def table = this.auctions

  override def getAllQuery =
    for {record <- table} yield record
}
