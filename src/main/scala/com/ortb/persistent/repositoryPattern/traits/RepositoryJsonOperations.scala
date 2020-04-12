package com.ortb.persistent.repositoryPattern.traits

trait RepositoryJsonOperations[T] extends RepositoryOperationsBase[T] {
  def add(json: String)

  def update(json: String)

  def toJson(entity: T): String

  def toJson(entity: Array[T]): String
}
