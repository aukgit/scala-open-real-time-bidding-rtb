package shared.com.ortb.model.routing

import shared.com.ortb.model.requests.AkkaRequestModel
import shared.io.helpers._

case class PlaceHolderRoutingModel(akkaRequest : AkkaRequestModel) extends TrimmedHttpRelativePath {
  lazy val routingStartingWithoutPlaceHolders : String =
    PathHelper
      .extractFirstPartOfPlaceHolderRouting(akkaRequest.relativePath)
  lazy val hasPlaceHolderRouting : Boolean = akkaServer.placeHoldersRouting.contains(routingStartingWithoutPlaceHolders)
  lazy val placeHolderRoute : String = akkaServer.placeHoldersRouting(routingStartingWithoutPlaceHolders)
  //  def placeHoldersMaps()  = {
  //    placeHolderRoute.
  //  }
}
