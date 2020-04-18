package services.core.traits

import shared.com.ortb.persistent.repositories.pattern.RepositoryBase

trait BasicPersistentServiceCore[TTable, TRow, TKey] {
  val repository : RepositoryBase[TTable, TRow, TKey]
}
