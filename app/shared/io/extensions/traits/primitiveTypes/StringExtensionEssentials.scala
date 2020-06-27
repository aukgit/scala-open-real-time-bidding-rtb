package shared.io.extensions.traits.primitiveTypes
import shared.io.extensions.TypeConvertExtensions._

trait StringExtensionEssentials {
  protected val s : String
  lazy val safeString : String = s.getOrElseDefault()
}
