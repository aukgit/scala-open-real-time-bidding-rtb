package shared.io.traits.jsonParse

import cats.kernel.Hash

trait JsonDecoders[T] {

  import io.circe._
  import io.circe.generic.semiauto._

  implicit val defaultDecoder : Decoder[T]
  val defaultIterableDecoder : Decoder[Iterable[T]] = deriveDecoder[Iterable[T]]
  val defaultSequenceDecoder : Decoder[Seq[T]] = deriveDecoder[Seq[T]]
  val defaultListDecoder : Decoder[List[T]] = deriveDecoder[List[T]]
  val defaultArrayDecoder : Decoder[Array[T]] = deriveDecoder[Array[T]]
  val defaultVectorDecoder : Decoder[Vector[T]] = deriveDecoder[Vector[T]]
  val defaultHashDecoder : Decoder[Hash[T]] = deriveDecoder[Hash[T]]
}
