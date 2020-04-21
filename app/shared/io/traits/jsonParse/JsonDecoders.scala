package shared.io.traits.jsonParse
import io.circe.generic.auto._
import io.circe.generic.codec.DerivedAsObjectCodec
import io.circe.generic.decoding.DerivedDecoder
import io.circe.generic.encoding.DerivedAsObjectEncoder
import io.circe.generic.semiauto.{deriveDecoder, _}
import io.circe.{Decoder, Encoder, _}
import shapeless.Lazy
import shared.com.ortb.implicits.implementations.CirceJsonSupport

class JsonCodec[T](
  implicit val decodeCodec : DerivedAsObjectCodec[T],
  val decodeListCodec : DerivedAsObjectCodec[List[T]])
  extends CirceJsonSupport {

  implicit def defaultCodec : Codec.AsObject[T] = deriveCodec[T](decodeCodec)

  implicit def defaultListCodec : Codec.AsObject[List[T]] =
    deriveCodec[List[T]](decodeListCodec)
}

class JsonDecoders[T]
(implicit val decoder : Lazy[DerivedDecoder[T]])
  extends CirceJsonSupport {

  implicit def defaultDecoder : Decoder[T] = deriveDecoder[T]

  implicit def defaultListDecoder : Decoder[List[T]] = deriveDecoder[List[T]]
}

class JsonEncoders[T]
(implicit val encoder : Lazy[DerivedAsObjectEncoder[T]])
  extends CirceJsonSupport {

  implicit def defaultDecoder : Encoder[T] = deriveEncoder[T]

  implicit def defaultListDecoder : Encoder[List[T]] = deriveEncoder[List[T]]
}

class JsonCirceEncoder[T]
(
  implicit val decodeCodec : DerivedAsObjectCodec[T],
  implicit val decoder : Lazy[DerivedDecoder[T]],
  val encoder : Lazy[DerivedAsObjectEncoder[T]],
  val decodeListCodec : DerivedAsObjectCodec[List[T]]){
  implicit def defaultCodec : Codec.AsObject[T] = deriveCodec[T](decodeCodec)

  implicit def defaultListCodec : Codec.AsObject[List[T]] =
    deriveCodec[List[T]](decodeListCodec)


  implicit def defaultDecoder : Decoder[T] = deriveDecoder[T]

  implicit def defaultListDecoder : Decoder[List[T]] = deriveDecoder[List[T]]

  implicit def defaultEncoder : Encoder[T] = deriveEncoder[T]

  implicit def defaultListEncoder : Encoder[List[T]] = deriveEncoder[List[T]]

  // implicit defaultDecoder = decoders.defaultDecoder
}

object WApp extends App with CirceJsonSupport {

  import shared.com.ortb.persistent.schema.Tables._

  val re = new JsonCirceEncoder[AuctionRow]
  //  val re3 = new JsonDecoders[Seq[AuctionRow]]

}
