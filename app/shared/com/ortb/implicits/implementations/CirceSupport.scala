package shared.com.ortb.implicits.implementations


import io.circe.{ Decoder, Encoder }
import shapeless.Unwrapped

class CirceSupport
  extends de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
    with AnyValCirceEncoding

object CirceSupport