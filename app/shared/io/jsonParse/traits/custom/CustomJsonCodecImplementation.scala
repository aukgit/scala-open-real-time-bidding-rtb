package shared.io.jsonParse.traits.custom

import io.circe.Decoder.Result
import io.circe._

trait CustomJsonCodecImplementation[T] extends CustomJsonCodec[T] {
  def getCodec : Codec[T] = new Codec[T] {
    /**
     * From Json String to Model
     *
     * @param c
     *
     * @return
     */
    override def apply(c : HCursor) : Result[T] = {
      val decoder = getDecoder
      decoder.apply(c)
    }

    /**
     * From model to Json Object
     *
     * @param a
     *
     * @return
     */
    override def apply(a : T) : Json = {
      val encoder = getEncoder
      encoder(a)
    }
  }
}
