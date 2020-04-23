package services.core.traits

import shared.com.repository.RepositoryBase
import shared.io.jsonParse.implementations.custom.models.{ ApiPaginationResultsModelCustomCodecImplementation, ControllerSuccessResultsModelCustomCodecImplementation }

trait ServiceRepositoryContract[TTable, TRow, TKey]{
  val repository : RepositoryBase[TTable, TRow, TKey]
}
