package shared.com.ortb.persistent.repositories.pattern.traits

import shared.com.ortb.model.results.RepositoryOperationResult
import shared.com.ortb.model.wrappers.persistent.EntityWrapperWithOptions

trait RepositoryJsonOperations[TRow, TKey]
    extends RepositoryOperationsBase[TRow] {
  def addUsingJson(json: String): RepositoryOperationResult[TRow, TKey]

  def addEntitiesUsingJson(json: String): RepositoryOperationResult[TRow, TKey]

  def updateUsingJson(json: String): RepositoryOperationResult[TRow, TKey]

  def updateEntitiesUsingJson(json: String): RepositoryOperationResult[TRow, TKey]
}
