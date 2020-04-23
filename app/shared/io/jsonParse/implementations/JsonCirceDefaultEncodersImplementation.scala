package shared.io.jsonParse.implementations

import io.circe.generic.codec.DerivedAsObjectCodec
import io.circe.generic.decoding.DerivedDecoder
import io.circe.generic.encoding.DerivedAsObjectEncoder
import io.circe.generic.semiauto.{ deriveDecoder, _ }
import io.circe.{ Decoder, _ }
import play.api.libs.json
import play.api.libs.json.JsValue
import shapeless.Lazy
import shared.io.helpers.EmptyValidateHelper
import shared.io.jsonParse.traits.JsonCirceDefaultEncoders
import shared.io.loggers.AppLogger

import scala.reflect.ClassTag

class JsonCirceDefaultEncodersImplementation[T](
  implicit val decodeCodec : DerivedAsObjectCodec[T],
  implicit val decoder : Lazy[DerivedDecoder[T]],
  implicit val classTag : ClassTag[T],
  implicit val decodeDeviceDecode : ClassTag[T],
  val encoder : Lazy[DerivedAsObjectEncoder[T]],
  val decodeListCodec : DerivedAsObjectCodec[List[T]]
) extends JsonCirceDefaultEncoders[T] {

  def defaultCodec : Codec.AsObject[T] = deriveCodec[T](decodeCodec)

  def defaultListCodec : Codec.AsObject[List[T]] =
    deriveCodec[List[T]](decodeListCodec)

  def defaultDecoder : Decoder[T] = deriveDecoder[T]

  def defaultListDecoder : Decoder[List[T]] = deriveDecoder[List[T]]

  def defaultEncoder : Encoder[T] = deriveEncoder[T]

  def defaultListEncoder : Encoder[List[T]] = deriveEncoder[List[T]]

  def toJson(entities : Iterable[T]) : JsValue = {
    json.Json.parse(toJsonString(entities))
  }

  def toJsonString(
    entities : Iterable[T],
    additionalAnnotationForItems : String = null) : String = {
    try {
      val toIterableJsonCollection : Iterable[Json] = toIterableJson(entities)
      val jsonStringCollection = toIterableJsonCollection.map(w => w.noSpaces)
      if (EmptyValidateHelper.isEmptyString(additionalAnnotationForItems)) {
        return jsonStringCollection.mkString("[", ",\n", "]")
      }

      val quote = "\""

      jsonStringCollection.mkString(
        s"{${ quote }${ additionalAnnotationForItems }${ quote }:[",
        ",\n",
        "]}")
    } catch {
      case e : Exception => AppLogger.errorCaptureAndThrow(e)
    }

    ""
  }

  def toIterableJson(entities : Iterable[T]) : Iterable[Json] = {
    entities.map(entity => defaultEncoder(entity))
  }

  override def getEncoder : Encoder[T] = defaultEncoder

  override def getDecoder : Decoder[T] = defaultDecoder
}
