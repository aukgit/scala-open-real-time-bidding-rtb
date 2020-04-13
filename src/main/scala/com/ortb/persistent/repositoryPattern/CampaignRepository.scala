package com.ortb.persistent.repositoryPattern

import slick.sql.{FixedSqlAction, FixedSqlStreamingAction}

import scala.concurrent._
import io.AppLogger
import slick.jdbc.SQLiteProfile.api._
import com.ortb.manager.AppManager
import com.ortb.model.results.RepositoryOperationResult
import com.ortb.persistent.schema
import com.ortb.persistent.schema.Tables
import com.ortb.persistent.schema.Tables._
import slick.dbio.{Effect, DBIOAction}
import slick.lifted.Query
//import scala.concurrent.ExecutionContext.Implicits.global

class CampaignRepository(appManager: AppManager) extends Repository[Campaign, CampaignRow, Int](appManager) {
  override def table = this.campaigns

  override def getAll: List[Tables.CampaignRow] = Await.result(getAllAsync, defaultTimeout).toList

  override def getById(entityId: Int): Tables.CampaignRow = {
    val records = (for (record <- table if record.campaignid === entityId) yield record).take(1)
    val x: FixedSqlStreamingAction[Seq[Tables.CampaignRow], Tables.CampaignRow, Effect.Read] = records.result
    val results = db.run(x)
    val finalResult = Await.result(results, defaultTimeout)
    this.getFirstOrDefault(finalResult)
  }

  override def getAllAsync: Future[Seq[CampaignRow]] = {
    val records = for {
      record <- table
    } yield record

    val results = db.run(records.result)
    AppLogger.logEntities(isLogQueries, results)
    results
  }

  override def runAsync(
    dbAction: DBIOAction[Seq[Tables.CampaignRow], NoStream, Effect.Write]
  ): Future[Seq[Tables.CampaignRow]] = {
    val results = db.run(dbAction)
    AppLogger.logEntities(isLogQueries, results)
    results
  }

  override def addAsync(entity: Tables.CampaignRow): Future[RepositoryOperationResult[Tables.CampaignRow]] = {
    this.executeDbActionAsync(
      entityKey = entity.campaignid,
      entity = entity,
      dbAction = table += entity,
      isPerformActionOnExist = false)
  }

  override def isExists(entityId: Int): Boolean = entityId > -1 && getById(entityId) != null

  override def delete(entity: Tables.CampaignRow):
  Future[RepositoryOperationResult[Tables.CampaignRow]] = {
    this.executeDbActionAsync(
      entityKey = entity.campaignid,
      entity = entity,
      dbAction = table.filter(c => c.campaignid === entity.campaignid).take(1).delete,
      isPerformActionOnExist = true)
  }

  override def addOrUpdate(entityId: Int, entity: Tables.CampaignRow): Future[RepositoryOperationResult[Tables.CampaignRow]] = {
    if (isExists(entityId)) {
      // update
      return updateAsync(entityId, entity)
    }

    // add
    addAsync(entity)
  }

  def getQueryById(id: Int): Query[Campaign, CampaignRow, Seq] = {
    table.filter(c => c.campaignid === id)
  }

  override def updateAsync(
    entityId: Int,
    entity: Tables.CampaignRow
  ):
  Future[RepositoryOperationResult[Tables.CampaignRow]] = {
    this.executeDbActionAsync (
      entity = entity,
      dbAction = getQueryById(entityId).take(1).update(entity)
    )
  }
}
