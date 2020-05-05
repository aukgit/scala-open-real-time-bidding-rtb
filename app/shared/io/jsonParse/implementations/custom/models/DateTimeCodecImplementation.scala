package shared.io.jsonParse.implementations.custom.models

import io.circe._
import org.joda.time.DateTime
import shared.io.jsonParse.traits.custom.CustomJsonCodecImplementation

class DateTimeCodecImplementation
  extends CustomJsonCodecImplementation[DateTime] {
  override def getEncoder : Encoder[DateTime] =
    (a : DateTime) => {
      Encoder.encodeLong.apply(a.getMillis)
    }

  override def getDecoder : Decoder[DateTime] = (c : HCursor) => {
    Decoder.decodeLong.map(s => DateTime.parse(c.toString)).apply(c)
  }
}
