package shared.com.repository.traits.adapters

import shared.com.ortb.model.wrappers.persistent.EntityWrapperWithOptions
import shared.com.repository.traits.operations.mutations.RepositoryOperationsBase

trait RepositoryJsonAdapter[TTable, TRow, TKey]
  extends RepositoryOperationsBase[TRow] {

  def fromEntityToJson(entity : Option[TRow])
  : Option[String]

  def fromEntitiesToJson(entities : Option[List[TRow]])
  : Option[String]

  def fromJsonToEntityWrapper(jsonContent : Option[String])
  : Option[EntityWrapperWithOptions[TRow, TKey]]

  def fromJsonToEntitiesWrapper(jsonContent : Option[String])
  : Option[List[EntityWrapperWithOptions[TRow, TKey]]]
}
