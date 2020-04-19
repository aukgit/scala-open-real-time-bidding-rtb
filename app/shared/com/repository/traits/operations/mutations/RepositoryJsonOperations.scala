package shared.com.repository.traits.operations.mutations

import shared.com.ortb.model.repository.response.RepositoryOperationResultModel

trait RepositoryJsonOperations[TRow, TKey]
    extends RepositoryOperationsBase[TRow] {
  def addUsingJson(json: String): RepositoryOperationResultModel[TRow, TKey]

  def addEntitiesUsingJson(json: String): RepositoryOperationResultModel[TRow, TKey]

  def updateUsingJson(json: String): RepositoryOperationResultModel[TRow, TKey]

  def updateEntitiesUsingJson(json: String): RepositoryOperationResultModel[TRow, TKey]
}
