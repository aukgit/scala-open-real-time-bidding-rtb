package shared.io.traits.jsonAdapter

import cats.kernel.Hash

trait JsonDecoders[T] {

  import io.circe._
  import io.circe.generic.semiauto._

  val defaultIterableDecoder : Decoder[Iterable[T]] = deriveDecoder
  val defaultSequenceDecoder : Decoder[Seq[T]] = deriveDecoder
  val defaultListDecoder : Decoder[List[T]] = deriveDecoder
  val defaultArrayDecoder : Decoder[Array[T]] = deriveDecoder
  val defaultVectorDecoder : Decoder[Vector[T]] = deriveDecoder
  val defaultHashDecoder : Decoder[Hash[T]] = deriveDecoder
}
