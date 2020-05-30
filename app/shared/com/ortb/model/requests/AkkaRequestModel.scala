package shared.com.ortb.model.requests

import akka.http.javadsl.model
import akka.http.scaladsl.model.{ HttpHeader, Uri }

case class AkkaRequestModel(
  endPointPrefix : String,
  uri : Uri,
  headers : Seq[HttpHeader],
  entity : model.RequestEntity) {
  lazy val query : Uri.Query = uri.query()
}
