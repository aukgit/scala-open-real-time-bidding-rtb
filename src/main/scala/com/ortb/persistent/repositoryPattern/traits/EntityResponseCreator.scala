package com.ortb.persistent.repositoryPattern.traits

import com.ortb.model.results.RepositoryOperationResult
import com.ortb.persistent.repositoryPattern.Repository
import io.AppLogger

import scala.concurrent.Future

trait EntityResponseCreator[TTable, TRow, TKey] {
  this : Repository[TTable, TRow, TKey] =>

  lazy protected val emptyResponse = new RepositoryOperationResult[TRow, TKey](
    false,
    entityId = None,
    entity = None)

  protected def createResponseFor(
    entityId  : Option[TKey],
    entity    : TRow,
    message : String = "",
    isSuccess : Boolean = true
  ) : RepositoryOperationResult[TRow, TKey] = {
    RepositoryOperationResult(isSuccess, entityId, Some(entity), message)
  }

  protected def createResponseForAffectedRow(
    affectedRow : Int,
    entityId : Option[TKey],
    entity    : TRow,
    message   : String = "",
    isSuccess : Boolean = true
  ) : RepositoryOperationResult[TRow, TKey] = {
    var message2 = message;
    if (message2.isEmpty) {
      message2 = "Operation successful."
    }

    if (affectedRow > 0) {
      AppLogger.logEntitiesNonFuture(isLogQueries, Seq(entity))
      return createResponseFor(entityId, entity, message2, isSuccess)
    }

    emptyResponse;
  }

  protected def getEmptyResponse : Future[RepositoryOperationResult[TRow, TKey]] = {
    AppLogger.conditionalInfo(isLogQueries, s"Operation Skipped.")

    Future {
      emptyResponse
    }
  }
}
