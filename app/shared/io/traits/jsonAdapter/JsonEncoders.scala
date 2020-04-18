package shared.io.traits.jsonAdapter

import cats.kernel.Hash

trait JsonEncoders[T] {

  import io.circe._
  import io.circe.generic.semiauto._

  val defaultIterableEncoder : Encoder[Iterable[T]] = deriveEncoder
  val defaultSequenceEncoder : Encoder[Seq[T]] = deriveEncoder
  val defaultListEncoder : Encoder[List[T]] = deriveEncoder
  val defaultArrayEncoder : Encoder[Array[T]] = deriveEncoder
  val defaultVectorEncoder : Encoder[Vector[T]] = deriveEncoder
  val defaultHashEncoder : Encoder[Hash[T]] = deriveEncoder
}
