package shared.io.traits.jsonParse

import cats.kernel.Hash
import io.circe.generic.auto._
import io.circe.{Decoder, _}
import io.circe.generic.codec.DerivedAsObjectCodec
import io.circe.generic.decoding.DerivedDecoder
import io.circe.generic.semiauto.{deriveDecoder, _}
import shapeless.Lazy
import shared.com.ortb.implicits.implementations.CirceJsonSupport

class JsonDecoders[T]
(implicit val decode : Lazy[DerivedDecoder[T]], val decodeCodec : DerivedAsObjectCodec[T])
  extends CirceJsonSupport {

  def defaultDecoder() : Decoder[T] = deriveDecoder[T]

  def codec : Codec.AsObject[T] = deriveCodec[T](decodeCodec)
}

object WApp extends App with CirceJsonSupport {

  import shared.com.ortb.persistent.schema.Tables._

  val re = new JsonDecoders[AuctionRow]
  val re2 = new JsonDecoders[List[AuctionRow]]
  val re3 = new JsonDecoders[Seq[AuctionRow]]

}