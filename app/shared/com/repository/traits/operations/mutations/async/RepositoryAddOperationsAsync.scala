package shared.com.repository.traits.operations.mutations.async

import shared.com.ortb.model.results.RepositoryOperationResultModel
import shared.com.repository.traits.operations.mutations.RepositoryOperationsBase

import scala.concurrent.Future

trait RepositoryAddOperationsAsync[TTable, TRow, TKey]
  extends RepositoryOperationsBase[TRow] {
  def addAsync(entity : TRow) : Future[RepositoryOperationResultModel[TRow, TKey]]
}
