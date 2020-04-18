package shared.com.ortb.persistent.repositories.pattern.traits.adapters

import shared.com.ortb.adapters.traits.BasicAdapterWithPartialFunction
import shared.com.ortb.model.wrappers.persistent.EntityWrapperWithOptions
import shared.com.ortb.persistent.repositories.pattern.traits.RepositoryOperationsBase

trait RepositoryJsonAdapter[TTable, TRow, TKey]
  extends
    RepositoryOperationsBase[TRow] with
    BasicAdapterWithPartialFunction[String, TRow] {

  def fromEntityToJson(entity : Option[TRow]) : Option[String]

  def fromEntitiesToJson(entities : Option[Iterable[TRow]]) : Option[String]

  def fromJsonToEntity(jsonContent : Option[String]) :
  Option[EntityWrapperWithOptions[TRow, TKey]]
}


