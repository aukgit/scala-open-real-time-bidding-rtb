package shared.com.repository.traits.implementions.operations.queries

import shared.com.repository.RepositoryBase
import shared.com.repository.traits.implementions._
import shared.com.repository.traits._
import shared.com.repository.traits.operations.queries._

trait SingleRepositoryBaseImplementation[TTable, TRow, TKey]
  extends SingleRepositoryBase[TTable, TRow, TKey]
    with RepositoryQueryOperationsImplementation[TTable, TRow, TKey]
    with RepositoryTableInfo[TTable, TRow, TKey]
    with RepositoryEntityGettersSetters[TTable, TRow, TKey]
    with RepositoryIdsGetterImplementation[TTable, TRow, TKey]
    with RepositoryGetQueryOperationsImplementation[TTable, TRow, TKey]
    with RepositoryEntityGettersSettersImplementation[TTable, TRow, TKey] {
  this : RepositoryBase[TTable, TRow, TKey] =>
}
