package shared.io.traits.jsonParse

import io.circe.generic.auto._ // must to import.
import io.circe.Codec
import io.circe.generic.codec.DerivedAsObjectCodec
import io.circe.generic.semiauto.deriveCodec
import shared.com.ortb.implicits.implementations.CirceJsonSupport

class JsonCodec[T](
  implicit val decodeCodec : DerivedAsObjectCodec[T],
  val decodeListCodec : DerivedAsObjectCodec[List[T]])
  extends CirceJsonSupport {

  implicit def defaultCodec : Codec.AsObject[T] = deriveCodec[T](decodeCodec)

  implicit def defaultListCodec : Codec.AsObject[List[T]] =
    deriveCodec[List[T]](decodeListCodec)
}
