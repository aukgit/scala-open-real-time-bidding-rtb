package shared.com.ortb.model.routing

import shared.com.ortb.model.requests.AkkaRequestModel

case class RoutingEnhancedModel(akkaRequest : AkkaRequestModel) extends TrimmedHttpRelativePath {
  lazy val commandRoute1 = s"${ routingPrefix }/commands"
  lazy val commandRoute2 = s"${ routingPrefix }/available-commands"
  lazy val commandUrlRoute = s"${ routingPrefix }/commands-url"
  lazy val helpRoute = s"${ routingPrefix }/help"

  lazy val isCommandRoute : Boolean = trimmedRelativePath.equalsIgnoreCase(commandRoute1) ||
    trimmedRelativePath.equalsIgnoreCase(commandRoute2)
  lazy val isCommandUrl : Boolean = trimmedRelativePath.equalsIgnoreCase(commandUrlRoute)
  lazy val isHelp : Boolean = trimmedRelativePath.equalsIgnoreCase(helpRoute)
}


