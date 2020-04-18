package shared.com.ortb.implicits

import cats.kernel.Hash
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

trait ImplicitJsonParser[T]{
  import io.circe._
  import io.circe.generic.semiauto._
  import io.circe._
  import io.circe.generic.auto._
  import io.circe.parser._
  import io.circe.generic.auto._
  import io.circe.syntax._
  import io.circe.Decoder.AccumulatingResult
  import io.circe.generic.JsonCodec

  val defaultIterableDecoder: Decoder[Iterable[T]] = deriveDecoder
  val defaultSequenceDecoder: Decoder[Seq[T]] = deriveDecoder
  val defaultListDecoder: Decoder[List[T]] = deriveDecoder
  val defaultArrayDecoder: Decoder[Array[T]] = deriveDecoder
  val defaultVectorDecoder: Decoder[Vector[T]] = deriveDecoder
  val defaultHashDecoder: Decoder[Hash[T]] = deriveDecoder
  
  val defaultIterableEncoder: Encoder[Iterable[T]] = deriveEncoder
  val defaultSequenceEncoder: Encoder[Seq[T]] = deriveEncoder
  val defaultListEncoder: Encoder[List[T]] = deriveEncoder
  val defaultArrayEncoder: Encoder[Array[T]] = deriveEncoder
  val defaultVectorEncoder: Encoder[Vector[T]] = deriveEncoder
  val defaultHashEncoder: Encoder[Hash[T]] = deriveEncoder
}

trait ImplicitJsonEncoders[T]{
  implicit val defaultEncoder: Encoder[T] = deriveEncoder
  implicit val defaultDecoder: Decoder[T] = deriveDecoder
}

trait JsonEncoders[T]{
  import io.circe._
  import io.circe.generic.semiauto._
  import io.circe._
  import io.circe.generic.auto._
  import io.circe.parser._
  import io.circe.generic.auto._
  import io.circe.syntax._
  import io.circe.Decoder.AccumulatingResult
  import io.circe.generic.JsonCodec
  val defaultIterableEncoder: Encoder[Iterable[T]] = deriveEncoder
  val defaultSequenceEncoder: Encoder[Seq[T]] = deriveEncoder
  val defaultListEncoder: Encoder[List[T]] = deriveEncoder
  val defaultArrayEncoder: Encoder[Array[T]] = deriveEncoder
  val defaultVectorEncoder: Encoder[Vector[T]] = deriveEncoder
  val defaultHashEncoder: Encoder[Hash[T]] = deriveEncoder
}



trait JsonDecoders[T]{
  import io.circe._
  import io.circe.generic.semiauto._
  import io.circe._
  import io.circe.generic.auto._
  import io.circe.parser._
  import io.circe.generic.auto._
  import io.circe.syntax._
  import io.circe.Decoder.AccumulatingResult
  import io.circe.generic.JsonCodec

  val defaultIterableDecoder: Decoder[Iterable[T]] = deriveDecoder
  val defaultSequenceDecoder: Decoder[Seq[T]] = deriveDecoder
  val defaultListDecoder: Decoder[List[T]] = deriveDecoder
  val defaultArrayDecoder: Decoder[Array[T]] = deriveDecoder
  val defaultVectorDecoder: Decoder[Vector[T]] = deriveDecoder
  val defaultHashDecoder: Decoder[Hash[T]] = deriveDecoder
}
