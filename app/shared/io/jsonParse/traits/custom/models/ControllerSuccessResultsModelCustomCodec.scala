package shared.io.jsonParse.traits.custom.models

import shared.com.ortb.model.results.ControllerSuccessResultsModel
import shared.io.jsonParse.traits.custom.CustomJsonCodecImplementation

trait ControllerSuccessResultsModelCustomCodec[TRow, TKey]
  extends CustomJsonCodecImplementation[ControllerSuccessResultsModel[TRow, TKey]]
