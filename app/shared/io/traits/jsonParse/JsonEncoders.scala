package shared.io.traits.jsonParse

import cats.kernel.Hash

trait JsonEncoders[T] {

  import io.circe._
  import io.circe.generic.semiauto._

  implicit val defaultEncoder : Encoder[T] = deriveEncoder[T]
  def defaultIterableEncoder : Encoder[Iterable[T]] = deriveEncoder[Iterable[T]]
  def defaultSequenceEncoder : Encoder[Seq[T]] = deriveEncoder[Seq[T]]
  def defaultListEncoder : Encoder[List[T]] = deriveEncoder[List[T]]
  def defaultArrayEncoder : Encoder[Array[T]] = deriveEncoder[Array[T]]
  def defaultVectorEncoder : Encoder[Vector[T]] = deriveEncoder[Vector[T]]
  def defaultHashEncoder : Encoder[Hash[T]] = deriveEncoder[Hash[T]]
}
