package com.ortb.persistent.repositories

import com.ortb.enumeration.DatabaseActionType
import slick.sql.FixedSqlStreamingAction

import scala.concurrent._
import io.AppLogger
import slick.jdbc.SQLiteProfile.api._
import com.ortb.manager.AppManager
import com.ortb.model.results.RepositoryOperationResult
import com.ortb.persistent.repositoryPattern.Repository
import com.ortb.persistent.schema.Tables
import com.ortb.persistent.schema.Tables._
import slick.collection.heterogeneous.Zero.+
import slick.dbio._
import slick.lifted.Query

class CampaignRepository(appManager: AppManager)
  extends
    Repository[Campaign, CampaignRow, Int](appManager) {
  override def table = this.campaigns

  override def getAll: List[Tables.CampaignRow] = Await.result(getAllAsync, defaultTimeout).toList

  override def getById(entityId: Int): Tables.CampaignRow = {
    this.run(getQueryByIdSingle(entityId)).head
  }

  override def getAllAsync: Future[Seq[CampaignRow]] = {
    val records = for {
      record <- table
    } yield record

    this.runAsync(records.result)
  }

  def getQueryById(id: Int): Query[Campaign, CampaignRow, Seq] = {
    table.filter(c => c.campaignid === id)
  }
}
