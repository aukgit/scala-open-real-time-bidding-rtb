package com.ortb.persistent.repositories

import scala.concurrent._
import slick.jdbc.SQLiteProfile.api._
import com.ortb.manager.AppManager
import com.ortb.persistent.repositoryPattern.Repository
import com.ortb.persistent.schema
import com.ortb.persistent.schema.Tables
import com.ortb.persistent.schema.Tables._
import slick.lifted.Query
import slick.sql.FixedSqlAction

class CampaignRepository(appManager : AppManager)
  extends
  Repository[Campaign, CampaignRow, Int](appManager) {

  override def table = this.campaigns

  override def tableName : String = this.campaignTableName

  override def getById(entityId : Int) : Tables.CampaignRow = {
    this.run(getQueryByIdSingle(entityId).result).head
  }

  override def getAllAsync : Future[Seq[CampaignRow]] = {
    this.runAsync(table.result)
  }

  def getQueryById(id : Int) : Query[Campaign, CampaignRow, Seq] = {
    table.filter(c => c.campaignid === id)
  }

  override def getAddAction(entity : CampaignRow) : SQLiteProfile.ProfileAction[CampaignRow, NoStream, Effect.Write]
  =
    table returning table.map(_.campaignid)  into ((entity, entityId) => entity.copy(campaignid = entityId)) += entity
}
