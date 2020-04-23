package services.core.traits

import shared.io.jsonParse.implementations.custom.models.{ ApiPaginationResultsModelCustomCodecImplementation, ControllerSuccessResultsModelCustomCodecImplementation }

trait ServiceEncodersContract[TTable, TRow, TKey] extends ServiceRepositoryContract[TTable, TRow, TKey]{
  lazy val apiPaginationEncoder = new ApiPaginationResultsModelCustomCodecImplementation[TRow, TKey](repository.encoders)
  lazy val controllerSuccessEncoder = new ControllerSuccessResultsModelCustomCodecImplementation[TRow, TKey](repository.encoders)
}
