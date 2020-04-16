package com.ortb.persistent.repositories.pattern.traits

import com.ortb.model.results.RepositoryOperationResult

trait RepositoryJsonOperations[TRow, TKey]
    extends RepositoryOperationsBase[TRow] {
  def add(json: String): RepositoryOperationResult[TRow, TKey]

  def update(json: String): RepositoryOperationResult[TRow, TKey]

  def toJson(entity: TRow): String

  def toJson(entity: Iterable[TRow]): String
}
