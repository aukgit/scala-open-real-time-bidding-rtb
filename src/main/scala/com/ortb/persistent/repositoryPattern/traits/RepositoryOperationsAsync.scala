package com.ortb.persistent.repositoryPattern.traits

import slick.sql.FixedSqlAction
import com.ortb.model.results.RepositoryOperationResult
import io.AppLogger

import scala.concurrent.Future
import slick.dbio.{NoStream, Effect}

trait RepositoryOperationsAsync[T, TKey] extends RepositoryOperationsBase[T] {
  def runAsync(dbAction: FixedSqlAction[Seq[T], NoStream, Effect.Write]): Future[Seq[T]]

  // def runSingleAsync(dbAction: FixedSqlAction[T, NoStream, Effect.Write]): Future[T]

  def saveAsync(dbAction: FixedSqlAction[Seq[T], NoStream, Effect.Write]): Future[RepositoryOperationResult[T]]

  def addAsync(entity: T): Future[RepositoryOperationResult[T]]

  def delete(entity: T): Future[RepositoryOperationResult[T]]

  def addOrUpdate(entityId: TKey, entity: T): Future[RepositoryOperationResult[T]]

  def updateAsync(entityId: TKey, entity: T): Future[RepositoryOperationResult[T]]
}
