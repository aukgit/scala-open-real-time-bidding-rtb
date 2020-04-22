package services.core.traits

import shared.com.repository.RepositoryBase
import shared.com.repository.traits.operations.mutations.RepositoryOperations

/**
 * Extends from ServiceRepositoryContract
 * @tparam TTable
 * @tparam TRow
 * @tparam TKey
 */
trait BasicPersistentServiceCore[TTable, TRow, TKey]
  extends ServiceRepositoryContract[TTable, TRow, TKey]
