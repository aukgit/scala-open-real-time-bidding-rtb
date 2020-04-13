package com.ortb.persistent.repositoryPattern.traits

import com.ortb.model.results.RepositoryOperationResult

trait EntityResponseCreator[TRow >: Null] {
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
}
