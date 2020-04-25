package services.core.traits

import shared.io.jsonParse.implementations.custom.models.{ ApiPaginationResultsModelCustomCodecImplementation, ControllerSuccessResultsModelCustomCodecImplementation }

trait ServiceEncoders[TTable, TRow, TKey] {
  val apiPaginationEncoder : ApiPaginationResultsModelCustomCodecImplementation[
    TRow,
    TKey]
  val controllerSuccessEncoder : ControllerSuccessResultsModelCustomCodecImplementation[
    TRow,
    TKey]
}
