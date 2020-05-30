package shared.com.ortb.serverAkka.framework.sampleCodes

import akka.http.javadsl.model
import akka.http.scaladsl.model._
import shared.com.ortb.model.config.core.ServiceBaseModel
import shared.com.ortb.model.requests.AkkaRequestModel
import shared.com.ortb.serverAkka.traits.AkkHttpServerContracts
import shared.com.ortb.serverAkka.traits.akkaMethods.AkkaGetPostMethod

import scala.concurrent.Future

class AkkaServerDefinition(
  val serviceModel : ServiceBaseModel,
  akkaGetPostMethod : AkkaGetPostMethod)
  extends AkkHttpServerContracts {

  def requestHandler : HttpRequest => Future[HttpResponse] = {
    case HttpRequest(HttpMethods.POST, uri @ Uri.Path(s"/api/$endPointPrefixes"), seqHeaders, entity, _) =>
      lazy val akkaRequest = AkkaRequestModel(endPointPrefixes, uri, seqHeaders, entity)
      akkaGetPostMethod.akkaPost.postEventual(akkaRequest)
      println("hello POST")
      println(seqHeaders)
      // println(entity.asJson.noSpaces)
      println(entity)
      println("Query")

      println(uri.query())
      Future {
        HttpResponse(
          status = StatusCodes.Accepted,
          entity = HttpEntity(
            ContentTypes.`text/html(UTF-8)`,
            "POST : You Get to Anything"
          ))
      }

    case HttpRequest(HttpMethods.GET, uri @ Uri.Path(s"/api/$endPointPrefixes"), seqHeaders, entity, _) =>
      println("hello GET")
      println(seqHeaders)
      println(entity)
      println("Query")
      println(uri.query())

      Future {
        HttpResponse(
          status = StatusCodes.Accepted,
          entity = HttpEntity(
            ContentTypes.`text/html(UTF-8)`,
            "You Get to Anything"
          ))
      }

    case request : HttpRequest =>
      request.discardEntityBytes()
      Future {
        HttpResponse(status = StatusCodes.NotFound)
      }
  }
}
