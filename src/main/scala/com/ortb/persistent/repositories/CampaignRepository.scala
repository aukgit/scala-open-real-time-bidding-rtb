package com.ortb.persistent.repositories

import scala.concurrent._
import slick.jdbc.SQLiteProfile.api._
import com.ortb.manager.AppManager
import com.ortb.persistent.repositoryPattern.Repository
import com.ortb.persistent.schema
import com.ortb.persistent.schema.Tables
import com.ortb.persistent.schema.Tables._
import slick.dbio.Effect
import slick.jdbc
import slick.jdbc.SQLiteProfile
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

  override def getAddAction(entity : CampaignRow) : FixedSqlAction[CampaignRow, NoStream, Effect.Write]
  =
  // votes returning votes.map(_.id) into ((vote, id) => vote.copy(id = Some(id))) += vote
    table returning table.map(_.campaignid) into
      ((entityx, entityId) => entityx.copy(campaignid = entityId)) += entity

  override def getIdOf(entity : CampaignRow) : Int = entity.campaignid
}
