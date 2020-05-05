package shared.io.jsonParse.implementations.custom.models

import io.circe.{ Decoder, Encoder, HCursor, Json }
import org.joda.time.DateTime
import shared.com.ortb.model.attributes.ApiPaginationResponseAttributesModel
import shared.com.ortb.model.results.ApiPaginationResultsModel
import shared.io.jsonParse.implementations.JsonCirceDefaultEncodersImplementation
import shared.io.jsonParse.traits.custom.CustomJsonCodecImplementation
import shared.io.jsonParse.traits.custom.models.ControllerSuccessResultsModelCustomCodec

class DateTimeCodecImplementation
  extends CustomJsonCodecImplementation[DateTime] {
  override def getEncoder : Encoder[DateTime] =
    (a : DateTime) =>
    return DateTime.parse(a)

  override def getDecoder : Decoder[DateTime] = (c : HCursor) =>
    DateTime.parse(c.toString)
}
