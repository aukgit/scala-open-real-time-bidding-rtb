package com.ortb.persistent.repositoryPattern.traits

import slick.sql.FixedSqlAction
import slick.dbio.{NoStream, Effect}
import com.ortb.model.results.RepositoryOperationResult

trait RepositoryOperations[T, TKey] extends RepositoryOperationsBase[T] {
  def run[TResult](dbAction: FixedSqlAction[T, NoStream, Effect.Write]): TResult

  def save(dbAction: FixedSqlAction[T, NoStream, Effect.Write]): RepositoryOperationResult[T]

  def add(entity: T): RepositoryOperationResult[T]

  def delete(entity: T): RepositoryOperationResult[T]

  def addOrUpdate(entityId: TKey, entity: T): RepositoryOperationResult[T]
}
