package services.core.traits

import shared.com.ortb.model.results.RepositoryOperationResultModel

trait BasicPersistentServiceOperations[TTable, TRow, TKey]
    extends BasicPersistentServiceCore[TTable, TRow, TKey] {
  def addUsingOption(
      entity: Option[TRow]): Option[RepositoryOperationResultModel[TRow, TKey]]

  def add(entity: TRow): RepositoryOperationResultModel[TRow, TKey]

  def getAll: Seq[TRow]

  def getAllList: List[TRow]

  def update(id: TKey, entity: TRow): RepositoryOperationResultModel[TRow, TKey]

  def addOrUpdate(id: TKey, entity: TRow): RepositoryOperationResultModel[TRow, TKey]

  def delete(id: TKey): RepositoryOperationResultModel[TRow, TKey]

  def getById(id: TKey): Option[TRow]
}
