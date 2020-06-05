package shared.com.ortb.model.routing

import shared.com.ortb.model.requests.AkkaRequestModel
import shared.com.ortb.serverAkka.traits.AkkHttpServerContracts
import shared.io.extensions.HttpExtensions._

trait TrimmedHttpRelativePath {
  /**
   * Relative path trimmed forward slash ending
   */
  lazy val trimmedRelativePath : String = akkaRequest.relativePath.safeTrimForwardSlashEnding
  lazy protected val akkaServer : AkkHttpServerContracts = akkaRequest.akkaServer

  lazy protected val routingPrefix : String = akkaServer.routingPrefix
  protected val akkaRequest : AkkaRequestModel
}
