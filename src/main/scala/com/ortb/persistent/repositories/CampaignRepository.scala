package com.ortb.persistent.repositories

import scala.concurrent._
import slick.jdbc.SQLiteProfile.api._
import com.ortb.manager.AppManager
import com.ortb.persistent.repositoryPattern.Repository
import com.ortb.persistent.schema.Tables
import com.ortb.persistent.schema.Tables._
import slick.dbio.Effect
import slick.lifted.Query
import slick.sql.FixedSqlAction

class CampaignRepository(appManager : AppManager)
  extends
    Repository[Campaign, CampaignRow, Int](appManager) {

  override def tableName : String = this.campaignTableName

  override def getById(entityId : Int) : Option[Tables.CampaignRow] = {
    val result = this.run(getQueryByIdSingle(entityId).result)
    if (result.isEmpty) {
      return None
    }

    Some(result.head)
  }

  override def getDeleteAction(
    entityId : Int) : FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getAllAsync : Future[Seq[CampaignRow]] = {
    val query = for {record <- table} yield record
    this.runAsync(query.result)
  }

  def getQueryById(id : Int) : Query[Campaign, CampaignRow, Seq] = {
    table.filter(c => c.campaignid === id)
  }

  override def getAddAction(entity : CampaignRow) : FixedSqlAction[CampaignRow, NoStream, Effect.Write]
  =
    table returning table.map(_.campaignid) into
      ((entityProjection, entityId) => entityProjection.copy(campaignid = entityId)) += entity

  override def table = this.campaigns

  override def getIdOf(entity : Option[CampaignRow]) : Int = if (entity.isDefined) entity.get.campaignid
                                                             else -1

  override def setEntityId(
    entityId : Option[Int],
    entity   : Option[CampaignRow]) : Option[CampaignRow] = {
    if (entityId.isEmpty || entity.isEmpty) {
      return None
    }

    Some(entity.get.copy(campaignid = entityId.get))
  }
}
