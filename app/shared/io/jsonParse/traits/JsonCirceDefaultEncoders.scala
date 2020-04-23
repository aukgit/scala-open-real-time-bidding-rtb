package shared.io.jsonParse.traits

import io.circe._
import play.api.libs.json.JsValue

trait JsonCirceDefaultEncoders[T] extends CirceJsonSupport with BasicJsonEncoder[T] {

 def defaultCodec : Codec.AsObject[T]

 def defaultListCodec : Codec.AsObject[List[T]]

 def defaultDecoder : Decoder[T]

 def defaultListDecoder : Decoder[List[T]]

 def defaultEncoder : Encoder[T]

 def defaultListEncoder : Encoder[List[T]]

  def toJson(entities : Iterable[T]) : JsValue

  def toJsonString(entities : Iterable[T], additionalAnnotationForItems: String = null) : String

  def toIterableJson(entities : Iterable[T]) : Iterable[Json]
}
