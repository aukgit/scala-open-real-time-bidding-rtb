package shared.io.traits.jsonAdapter

import shared.com.ortb.implicits._

trait JsonParser[T]
  extends ImplicitJsonParser[T]
    with JsonEncoders[T]
    with JsonDecoders[T]
