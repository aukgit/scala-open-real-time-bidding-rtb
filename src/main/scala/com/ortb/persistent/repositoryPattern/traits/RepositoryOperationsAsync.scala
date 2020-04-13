package com.ortb.persistent.repositoryPattern.traits

import slick.sql.{FixedSqlAction, FixedSqlStreamingAction}
import com.ortb.model.results.RepositoryOperationResult
import io.AppLogger

import scala.concurrent.Future
import slick.dbio.{NoStream, Effect, DBIOAction}

trait RepositoryOperationsAsync[TRow, TKey] extends RepositoryOperationsBase[TRow] {
  def runAsync(dbAction: DBIOAction[Seq[TRow], NoStream, Effect.Write]): Future[Seq[TRow]]

  // def runAsync(dbAction: FixedSqlStreamingAction[Seq[TRow], NoStream, Effect.Write]): Future[Seq[TRow]]

  // def runSingleAsync(dbAction: FixedSqlAction[T, NoStream, Effect.Write]): Future[T]

  // def saveAsync(dbAction: FixedSqlAction[Seq[TRow], NoStream, Effect.Write]): Future[RepositoryOperationResult[TRow]]

  def addAsync(entity: TRow): Future[RepositoryOperationResult[TRow]]

  def delete(entity: TRow): Future[RepositoryOperationResult[TRow]]

  def addOrUpdate(entityId: TKey, entity: TRow): Future[RepositoryOperationResult[TRow]]

  def updateAsync(entityId: TKey, entity: TRow): Future[RepositoryOperationResult[TRow]]
}
