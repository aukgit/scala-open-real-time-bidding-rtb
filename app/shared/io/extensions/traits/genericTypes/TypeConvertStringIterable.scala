package shared.io.extensions.traits.genericTypes

import io.circe.Json
import shared.io.helpers.JsonHelper

trait TypeConvertStringIterable extends TypeConvertGenericIterable[String] {
  lazy val toJsonObject : Json = JsonHelper.toJson(anyItems).get
  lazy val toJsonString : String = toJsonObject.noSpaces
  lazy val toJsonStringPretty : String = toJsonObject.spaces2
}
