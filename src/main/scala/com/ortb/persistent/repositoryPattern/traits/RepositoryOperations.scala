package com.ortb.persistent.repositoryPattern.traits

import slick.sql.FixedSqlAction
import slick.dbio.{NoStream, Effect}
import com.ortb.model.results.RepositoryOperationResult

trait RepositoryOperations[TRow, TKey] extends RepositoryOperationsBase[TRow] {
  def run[TResult](dbAction: FixedSqlAction[TRow, NoStream, Effect.Write]): TResult

  def save(dbAction: FixedSqlAction[TRow, NoStream, Effect.Write]): RepositoryOperationResult[TRow]

  def add(entity: TRow): RepositoryOperationResult[TRow]

  def delete(entity: TRow): RepositoryOperationResult[TRow]

  def addOrUpdate(entityId: TKey, entity: TRow): RepositoryOperationResult[TRow]
}
