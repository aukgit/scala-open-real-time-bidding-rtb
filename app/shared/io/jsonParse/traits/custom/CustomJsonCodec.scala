package shared.io.jsonParse.traits.custom

import io.circe.Codec
import shared.io.jsonParse.traits.BasicJsonEncoder

trait CustomJsonCodec[T] extends BasicJsonEncoder[T] {
  def getCodec : Codec[T]
}
