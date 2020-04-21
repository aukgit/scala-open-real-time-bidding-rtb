package shared.io.traits.jsonParse
import io.circe.generic.auto._ // must to import.
import io.circe.generic.decoding.DerivedDecoder
import io.circe.generic.semiauto.{deriveDecoder, _}
import io.circe.{Decoder, Encoder, _}
import shapeless.Lazy
import shared.com.ortb.implicits.implementations.CirceJsonSupport

class JsonDecoders[T]
(implicit val decoder : Lazy[DerivedDecoder[T]])
  extends CirceJsonSupport {

  implicit def defaultDecoder : Decoder[T] = deriveDecoder[T]

  implicit def defaultListDecoder : Decoder[List[T]] = deriveDecoder[List[T]]
}

object WApp extends App with CirceJsonSupport {

  import shared.com.ortb.persistent.schema.Tables._

  val re = new JsonCirceDefaultEncoders[AuctionRow]
  //  val re3 = new JsonDecoders[Seq[AuctionRow]]

}
