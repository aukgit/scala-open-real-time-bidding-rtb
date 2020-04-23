package services.core.traits

import shared.io.jsonParse.implementations.custom.models.{ ApiPaginationResultsModelCustomCodecImplementation, ControllerSuccessResultsModelCustomCodecImplementation }

class ServiceEncodersImplementation[TTable, TRow, TKey](
    serviceRepositoryContract: ServiceRepositoryContract[TTable, TRow, TKey])
    extends ServiceEncoders[TTable, TRow, TKey] {
  lazy private val repository = serviceRepositoryContract.repository
  lazy private val encoders = repository.encoders
  lazy val apiPaginationEncoder
    : ApiPaginationResultsModelCustomCodecImplementation[TRow, TKey] =
    new ApiPaginationResultsModelCustomCodecImplementation[TRow, TKey](encoders)
  lazy val controllerSuccessEncoder
    : ControllerSuccessResultsModelCustomCodecImplementation[TRow, TKey] =
    new ControllerSuccessResultsModelCustomCodecImplementation[TRow, TKey](
      encoders)
}
