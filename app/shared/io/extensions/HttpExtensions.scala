package shared.io.extensions

import shared.io.extensions.traits.HttpStringExtension

object HttpExtensions {

  implicit class HttpStringExtensionConcrete(protected val s : String) extends HttpStringExtension

}
