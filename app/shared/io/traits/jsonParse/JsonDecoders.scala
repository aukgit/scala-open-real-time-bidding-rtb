package shared.io.traits.jsonParse

import cats.kernel.Hash

trait JsonDecoders[T <: CaseClass] {

  import io.circe._
  import io.circe.generic.semiauto._

  implicit val defaultDecoder : Decoder[T]
  def defaultIterableDecoder : Decoder[Iterable[T]] = deriveDecoder[Iterable[T]]
  def defaultSequenceDecoder : Decoder[Seq[T]] = deriveDecoder[Seq[T]]
  def defaultListDecoder : Decoder[List[T]] = deriveDecoder[List[T]]
  def defaultArrayDecoder : Decoder[Array[T]] = deriveDecoder[Array[T]]
  def defaultVectorDecoder : Decoder[Vector[T]] = deriveDecoder[Vector[T]]
  def defaultHashDecoder : Decoder[Hash[T]] = deriveDecoder[Hash[T]]
}
