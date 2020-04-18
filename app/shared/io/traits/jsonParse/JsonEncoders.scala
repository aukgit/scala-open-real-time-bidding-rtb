package shared.io.traits.jsonParse

import cats.kernel.Hash

trait JsonEncoders[T] {

  import io.circe._
  import io.circe.generic.semiauto._

  implicit val defaultEncoder : Encoder[T] = deriveEncoder[T]
  val defaultIterableEncoder : Encoder[Iterable[T]] = deriveEncoder[Iterable[T]]
  val defaultSequenceEncoder : Encoder[Seq[T]] = deriveEncoder[Seq[T]]
  val defaultListEncoder : Encoder[List[T]] = deriveEncoder[List[T]]
  val defaultArrayEncoder : Encoder[Array[T]] = deriveEncoder[Array[T]]
  val defaultVectorEncoder : Encoder[Vector[T]] = deriveEncoder[Vector[T]]
  val defaultHashEncoder : Encoder[Hash[T]] = deriveEncoder[Hash[T]]
}
