package shared.com.ortb.serverAkka.framework.traits

import akka.http.scaladsl.model.HttpResponse
import shared.com.ortb.model.requests.AkkaRequestModel

trait AkkaNonImplementGetMethod extends AkkaGetMethod {
  def get(akkaRequest : AkkaRequestModel) : HttpResponse = throw new NotImplementedError(s"GET ${ akkaRequest.endPointPrefix } not supported.")
}
