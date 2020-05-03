package shared.com.ortb.model.results

import shared.com.ortb.model.attributes.GenericResponseAttributesModel
import shared.com.ortb.model.wrappers.persistent.EntityWrapperModel

case class RepositoryOperationResultsModel[TRow, TKey](
  attributes : Option[GenericResponseAttributesModel],
  data : Iterable[EntityWrapperModel[TRow, TKey]])
