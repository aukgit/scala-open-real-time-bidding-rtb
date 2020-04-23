package services.core.traits

/**
 * Extends from ServiceRepositoryContract
 *
 * @tparam TTable
 * @tparam TRow
 * @tparam TKey
 */
trait BasicPersistentServiceCore[TTable, TRow, TKey]
  extends ServiceRepositoryContract[TTable, TRow, TKey]
    with ServiceEncodersContract[TTable, TRow, TKey]
