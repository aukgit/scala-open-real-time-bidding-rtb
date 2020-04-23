package shared.io.jsonParse.traits.custom.models

import shared.com.ortb.model.repository.response._
import shared.io.jsonParse.traits.custom.CustomJsonCodecImplementation

trait ApiPaginationResultsModelCustomCodec[TRow, TKey]
  extends CustomJsonCodecImplementation[ApiPaginationResultsModel[TRow, TKey]]


