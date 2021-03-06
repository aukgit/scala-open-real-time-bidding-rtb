package shared.io.jsonParse.traits.custom.models

import shared.com.ortb.model.results.ApiPaginationResultsModel
import shared.io.jsonParse.traits.custom.CustomJsonCodecImplementation

trait ApiPaginationResultsModelCustomCodec[TRow, TKey]
  extends CustomJsonCodecImplementation[ApiPaginationResultsModel[TRow, TKey]]




