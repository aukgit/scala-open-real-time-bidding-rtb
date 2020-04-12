package com.ortb.persistent.repositoryPattern

import com.ortb.persistent.schema.Tables
import slick.sql.FixedSqlAction

import scala.concurrent.{Await, Future}
import com.ortb.model.results.RepositoryOperationResult
import io.AppLogger
import slick.dbio.{NoStream, Effect}
import com.ortb.manager.AppManager
import com.ortb.persistent.repositoryPattern.traits.Repository

class CampaignRepository(appManager: AppManager) extends Repository[CampaignRow, Int](appManager) {
  override def table = this.campaigns

  override def getFindBy(f: Tables.CampaignRow => Boolean): Tables.CampaignRow = this.table.filter(f)

  override def getAll: List[Tables.CampaignRow] = {
    Await.result(getAllAsync, defaultTimeout).toList
  }

  override def getById(entityId: Int): Tables.CampaignRow = ???

  override def getFindByAsync(f: Tables.CampaignRow => Boolean): Future[Tables.CampaignRow] = ???

  override def getAllAsync: Future[Seq[CampaignRow]] = {
    val records = for {
      record <- table
    } yield record

    val results = appManager.getDb.run(records.result)
    AppLogger.logEntities(isLogQueries, results)
    results
  }

  override def getByIdAsync(entityId: Int): Future[Tables.CampaignRow] = {
    val records = (for (record <- table if record.campaignid === entityId) yield record).take(1)
    val results: Future[Seq[Tables.CampaignRow]] = runAsync(records.result.head)

    results
  }

  override def runAsync(
    dbAction: FixedSqlAction[Seq[Tables.CampaignRow], NoStream, Effect.Write]
  ): Future[Seq[Tables.CampaignRow]] = {
    val results = appManager.getDb.run(dbAction)
    AppLogger.logEntities(isLogQueries, results)
    results
  }

  // override def saveAsync(dbAction: FixedSqlAction[Tables.CampaignRow, NoStream, Effect.Write]) = ???

  override def addAsync(entity: Tables.CampaignRow): Future[RepositoryOperationResult[Tables.CampaignRow]] = ???

  override def delete(entity: Tables.CampaignRow): Future[RepositoryOperationResult[Tables.CampaignRow]] = ???

  override def addOrUpdate(entityId: Int, entity: Tables.CampaignRow): Unit = ???
}
