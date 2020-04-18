package shared.io.traits.jsonParse

trait JsonParser[T]
  extends JsonEncoders[T] with JsonDecoders[T]
