package services.core.traits

import shared.com.repository.RepositoryBase

trait BasicPersistentServiceCore[TTable, TRow, TKey] {
  val repository : RepositoryBase[TTable, TRow, TKey]
}
