package shared.io.jsonParse.traits

import io.circe.Codec

trait JsonCodec[T]
  extends CirceJsonSupport {

  implicit def defaultCodec : Codec.AsObject[T]

  implicit def defaultListCodec : Codec.AsObject[List[T]]
}
