package com.ortb.persistent.repositoryPattern

import com.ortb
import com.ortb.persistent.schema.Tables
import slick.sql.FixedSqlAction

import scala.concurrent._
import io.AppLogger
import slick.dbio.{NoStream, Effect}
import slick.jdbc.SQLiteProfile.api._
import com.ortb.manager.AppManager
import com.ortb.model.results.RepositoryOperationResult
import com.ortb.persistent
import com.ortb.persistent.schema
import com.ortb.persistent.schema.Tables._
import slick.lifted.Query
//import scala.concurrent.ExecutionContext.Implicits.global

class CampaignRepository(appManager: AppManager) extends Repository[Campaign, CampaignRow, Int](appManager) {
  override def table = this.campaigns

  override def getAll: List[Tables.CampaignRow] = Await.result(getAllAsync, defaultTimeout).toList

  override def getById(entityId: Int): Tables.CampaignRow = {
    val records = (for (record <- table if record.campaignid === entityId) yield record).take(1)
    val results = this.runAsync(records.result)
    val finalResult = Await.result(results, defaultTimeout)
    this.getFirstOrDefault(finalResult)
  }

  override def getAllAsync: Future[Seq[CampaignRow]] = {
    val records = for {
      record <- table
    } yield record

    val results = appManager.getDb.run(records.result)
    AppLogger.logEntities(isLogQueries, results)
    results
  }

  override def runAsync(
    dbAction: FixedSqlAction[Seq[Tables.CampaignRow], NoStream, Effect.Write]
  ): Future[Seq[Tables.CampaignRow]] = {
    val results = db.run(dbAction)
    AppLogger.logEntities(isLogQueries, results)
    results
  }

  override def addAsync(entity: Tables.CampaignRow): Future[RepositoryOperationResult[schema.Tables.CampaignRow]] = {
    this.executeDbActionAsync(
      entityKey = entity.campaignid,
      entity = entity,
      dbAction = table += entity,
      isPerformActionOnExist = false)
  }

  override def isExists(entityId: Int): Boolean = entityId > -1 && getById(entityId) != null

  override def delete(entity: schema.Tables.CampaignRow):
  Future[RepositoryOperationResult[schema.Tables.CampaignRow]] = {
    this.executeDbActionAsync(
      entityKey = entity.campaignid,
      entity = entity,
      dbAction = table.filter(c => c.campaignid === entity.campaignid).take(1).delete,
      isPerformActionOnExist = true)
  }

  override def addOrUpdate(entityId: Int, entity: schema.Tables.CampaignRow): Future[RepositoryOperationResult[schema.Tables.CampaignRow]] = {
    if (isExists(entityId)) {
      // update
      return updateAsync(entityId, entity)
    }

    // add
    addAsync(entity)
  }

  def getById(id: Int): Query[Campaign, CampaignRow, Seq] = {
    table.filter(c => c.campaignid === id)
  }

  override def updateAsync(
    entityId: Int,
    entity: schema.Tables.CampaignRow
  ):
  Future[RepositoryOperationResult[schema.Tables.CampaignRow]] = {
    return this executeDbActionAsync (
      entityKey = entity.campaignid,
      entity = entity,
      dbAction =.take(1).update(entity)
    isPerformActionOnExist = false
    )
  }
}
