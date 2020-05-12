package shared.io.jsonParse.traits

import io.circe.Decoder

trait JsonDecoders[T]
  extends CirceJsonSupport {

  def defaultDecoder : Decoder[T]

  def defaultListDecoder : Decoder[List[T]]
}
