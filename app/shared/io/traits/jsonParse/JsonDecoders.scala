package shared.io.traits.jsonParse

import java.io.Serializable

import cats.kernel.Hash
import io.circe.Encoder
import io.circe.generic.semiauto._
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.generic.auto._
import io.circe.generic.encoding.DerivedAsObjectEncoder
import io.circe.generic.encoding._
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._
import io.circe.syntax._
import io.circe.Decoder.{AccumulatingResult, Result}
import io.circe.generic.JsonCodec
import io.circe.generic.codec.DerivedAsObjectCodec
import io.circe.generic.decoding.DerivedDecoder
import shapeless.{Lazy, tag}

import scala.reflect.ClassTag

  // (implicit encodeTRow: Encoder[TRow])
class
  JsonDecoders[T]
  (implicit decode: Lazy[DerivedDecoder[T]],  decodeCodec: DerivedAsObjectCodec[T])
//(implicit decode: DerivedAsObjectCodec[T])
//  (implicit decodeCodec : Lazy[DerivedAsObjectCodec[T]], decodeCodec2 : Lazy[DerivedAsObjectCodec[Iterable[T]]])
{

  import io.circe._
  import io.circe.generic.semiauto._

//   implicit val defaultCodec : Codec.AsObject[T] = deriveCodec[T]
  import scala.reflect.runtime.{universe => ru}
//  val x = new DerivedDecoder[T]
    def defaultDecoder() : Decoder[T] = deriveDecoder[T]
   def codec : Codec.AsObject[T] = deriveCodec[T](decodeCodec)
//  def codecIterable = deriveCodec[Iterable[T]](decodeCodec2)
//  def defaultIterableDecoder : Decoder[Iterable[T]] = deriveDecoder[Iterable[T]](decode)
//  def defaultSequenceDecoder : Decoder[Seq[T]] = deriveDecoder
//  def defaultListDecoder : Decoder[List[T]] = deriveDecoder
//  def defaultArrayDecoder : Decoder[Array[T]] = deriveDecoder[Array[T]]
//  def defaultVectorDecoder : Decoder[Vector[T]] = deriveDecoder[Vector[T]]
//  def defaultHashDecoder : Decoder[Hash[T]] = deriveDecoder[Hash[T]]
}

object WApp extends App{
  import io.circe.generic.semiauto._
  import io.circe._
  import io.circe.generic.auto._
  import io.circe.parser._
  import io.circe.generic.auto._
  import io.circe.generic.encoding.DerivedAsObjectEncoder
  import io.circe.generic.encoding._
  import io.circe.{ Decoder, Encoder }, io.circe.generic.semiauto._
  import io.circe.{Decoder, Encoder}
  import io.circe.generic.semiauto._
  import io.circe.syntax._
  import io.circe.Decoder.AccumulatingResult
  import io.circe.generic.JsonCodec
  import io.circe._
  import io.circe.generic.semiauto._


  import shared.com.ortb.persistent.schema.Tables._
  def getJsonDecoder[T : ClassTag] : JsonDecoders[T](implicit tag: ClassTag[T]) = {
    import scala.reflect._
    import scala.reflect.runtime.universe._

//    val r = deriveDecoder[Iterable[AuctionRow]]

    val classTag = tag.runTime.asInstanceOf[Class[T]]

    val m = new JsonDecoders[T]()

    return m.asInstanceOf[JsonDecoders[T]]
    // val w = scala.reflect.ClassTag[T]
//    val w : Class[T] = classOf[T]
//
//    val x = new JsonDecoders[T]()(w)
//
//    return x
  }

  val re = getJsonDecoder[AuctionRow]
}