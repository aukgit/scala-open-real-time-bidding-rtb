package com.ortb.persistent.repositoryPattern.traits

import akka.util.Timeout
import com.ortb
import com.ortb.manager.AppManager
import com.ortb.model.config.ConfigModel
import com.ortb.model.results.RepositoryOperationResult
import com.ortb.persistent
import com.ortb.persistent.schema
import com.ortb.persistent.schema.{DatabaseSchema, Tables}
import com.ortb.persistent.schema.Tables.CampaignRow
import io.AppLogger
import slick.dbio.{NoStream, Effect}
import slick.sql.FixedSqlAction
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.duration._
import scala.concurrent.{Future, Await, ExecutionContextExecutor}

trait SingleRepositoryBase[TRow, TKey]
  extends
    DatabaseSchema with
    RepositoryOperationsAsync[TRow, TKey] {

  def table: Any

  def getFindBy(f: TRow => Boolean): TRow

  def getAll: List[TRow]

  def getById(entityId: TKey): TRow

  def getFindByAsync(f: TRow => Boolean): Future[TRow]

  def getAllAsync: Future[Seq[TRow]]

  def getByIdAsync(entityId: TKey): Future[TRow]
}

abstract class Repository[TRow, TKey](appManager: AppManager) extends SingleRepositoryBase[TRow, TKey] {
  lazy val executionContext: ExecutionContextExecutor = appManager.executionContextManager.createDefault()
  lazy val config: ConfigModel = appManager.config
  /**
   * default timeout from config, if < 0 then infinite
   */
  lazy implicit val defaultTimeout: Duration = if (config.defaultTimeout < 0) Duration.Inf else config.defaultTimeout.seconds
  lazy val isLogQueries: Boolean = config.isLogDatabaseQueryLogs
}


