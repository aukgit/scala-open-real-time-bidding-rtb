package shared.com.repository.traits.operations.mutations

import shared.com.ortb.model.results.RepositoryOperationResult

trait RepositoryJsonOperations[TRow, TKey]
    extends RepositoryOperationsBase[TRow] {
  def addUsingJson(json: String): RepositoryOperationResult[TRow, TKey]

  def addEntitiesUsingJson(json: String): RepositoryOperationResult[TRow, TKey]

  def updateUsingJson(json: String): RepositoryOperationResult[TRow, TKey]

  def updateEntitiesUsingJson(json: String): RepositoryOperationResult[TRow, TKey]
}
