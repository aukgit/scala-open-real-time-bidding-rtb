package shared.com.ortb.model.requests

import akka.http.scaladsl.model._

case class AkkaRequestModel(
  endPointPrefix : String,
  uri : Uri,
  headers : Seq[HttpHeader],
  entity : RequestEntity) {
  lazy val query : Uri.Query = uri.query()
}
