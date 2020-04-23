package services.core.traits

import shared.com.repository.RepositoryBase
import shared.io.jsonParse.implementations.custom.models.ApiPaginationResultsModelCustomCodecImplementation

trait ServiceRepositoryContract[TTable, TRow, TKey]{
  val repository : RepositoryBase[TTable, TRow, TKey]
  lazy val apiPaginationEncoder = new ApiPaginationResultsModelCustomCodecImplementation[TRow, TKey](repository.encoders)
}
