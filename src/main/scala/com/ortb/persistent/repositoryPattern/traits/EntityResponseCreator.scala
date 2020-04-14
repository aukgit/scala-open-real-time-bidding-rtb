package com.ortb.persistent.repositoryPattern.traits

import com.ortb.model.results.RepositoryOperationResult
import com.ortb.persistent.repositoryPattern.Repository
import io.AppLogger
import slick.lifted.{AbstractTable, TableQuery}

import scala.concurrent.Future

trait EntityResponseCreator[TTable, TRow, TKey] {
  this: Repository[TTable, TRow, TKey] =>

  lazy protected val emptyResponse = new RepositoryOperationResult[TRow](false, entity = None)

  protected def createResponseFor(
                                   entity: TRow,
                                   message: String = "",
                                   isSuccess: Boolean = true
                                 ): RepositoryOperationResult[TRow] = {
    new RepositoryOperationResult[TRow](isSuccess, Some(entity), message)
  }

  protected def createResponseForAffectedRow(
                                              affectedRow: Int,
                                              entity: TRow,
                                              message: String = "",
                                              isSuccess: Boolean = true
                                            ): RepositoryOperationResult[TRow] = {
    var message2 = message;
    if (message2.isEmpty) {
      message2 = "Operation successful."
    }

    if (affectedRow > 0) {
      return createResponseFor(entity, message2, isSuccess)
    }

    emptyResponse;
  }

  protected def getEmptyResponse: Future[RepositoryOperationResult[TRow]] = {
    AppLogger.conditionalInfo(isLogQueries, s"Operation Skipped.")

    Future {
      emptyResponse
    }
  }
}
