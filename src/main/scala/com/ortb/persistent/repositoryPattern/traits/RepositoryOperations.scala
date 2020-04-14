package com.ortb.persistent.repositoryPattern.traits

import com.ortb.model.results.RepositoryOperationResult
import com.ortb.persistent.repositoryPattern.Repository
import io.AppLogger

import scala.collection.mutable.ListBuffer

trait RepositoryOperations[TTable, TRow, TKey] extends
  RepositoryOperationsBase[TRow] {
  this : Repository[TTable, TRow, TKey] =>

  def add(entity : TRow) : RepositoryOperationResult[TRow, TKey] =
    toRegular(addAsync(entity), defaultTimeout)

  def update(entityId : TKey, entity : TRow) : RepositoryOperationResult[TRow, TKey] =
    toRegular(updateAsync(entityId, entity), defaultTimeout)

  def delete(entityId : TKey) : RepositoryOperationResult[TRow, TKey] =
    toRegular(deleteAsync(entityId), defaultTimeout)

  def addOrUpdate(entityId : TKey, entity : TRow) : RepositoryOperationResult[TRow, TKey] =
    toRegular(addOrUpdateAsync(entityId, entity), defaultTimeout)


  def addEntities(entities : Iterable[TRow]) : Iterable[RepositoryOperationResult[TRow, TKey]] = {
    if (entities.isEmpty) {
      AppLogger.info("no items passed for adding.")
    }

    entities.map(entity => toRegular(this.addAsync(entity), defaultTimeout))
  }

  def deleteEntities(entities : Iterable[TKey]) : Iterable[RepositoryOperationResult[TRow, TKey]] = {
    if (entities.isEmpty) {
      AppLogger.info("no items passed for deleting.")
    }

    entities.map(entity => toRegular(this.deleteAsync(entity), defaultTimeout))
  }

  def addEntities(entity : TRow, addTimes : Int) :
  Iterable[RepositoryOperationResult[TRow, TKey]] = {
    if (entity == null) {
      AppLogger.info("no items passed for adding.")
    }

    val list = ListBuffer[RepositoryOperationResult[TRow, TKey]]()
    for (i <- 0 until addTimes) {
      val response = toRegular(this.addAsync(entity), defaultTimeout)
      list += response
    }

    list
  }
}
