package com.ortb.persistent.repositoryPattern.traits

import com.ortb.model.results.RepositoryOperationResult

trait RepositoryJsonOperations[T] extends RepositoryOperationsBase[T] {
  def add(json: String): RepositoryOperationResult[T]

  def update(json: String): RepositoryOperationResult[T]

  def toJson(entity: T): String

  def toJson(entity: Iterable[T]): String
}
