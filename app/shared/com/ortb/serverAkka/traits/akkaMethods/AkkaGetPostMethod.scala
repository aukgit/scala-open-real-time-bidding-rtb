package shared.com.ortb.serverAkka.traits.akkaMethods

import akka.http.scaladsl.model.HttpResponse
import shared.com.ortb.model.requests.AkkaRequestModel

trait AkkaGetPostMethod extends AkkaPostMethod with AkkaGetMethod {
  protected val akkaPost : AkkaPostMethod
  protected val akkaGet : AkkaGetMethod

  def post(akkaRequest : AkkaRequestModel) : HttpResponse = akkaPost.post(akkaRequest)

  def get(akkaRequest : AkkaRequestModel) : HttpResponse = akkaGet.get(akkaRequest)
}


