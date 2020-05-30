package shared.com.ortb.serverAkka.framework.sampleCodes

import akka.http.javadsl.model
import akka.http.scaladsl.model._
import shared.com.ortb.model.config.core.ServiceBaseModel

import scala.concurrent.Future

case class AkkaRequest(
  endPointPrefix : String,
  uri : Uri,
  headers : Seq[HttpHeader],
  entity : model.RequestEntity) {
  lazy val query : Uri.Query = uri.query()
}

trait AkkaGetMethod {
  def get(akkaRequest : AkkaRequest) : HttpResponse

  def getEventual(akkaRequest : AkkaRequest) : Future[HttpResponse]
  = Future {
    get(akkaRequest)
  }
}


trait AkkaPostMethod {
  def post(akkaRequest : AkkaRequest) : HttpResponse

  def postEventual(akkaRequest : AkkaRequest) : Future[HttpResponse]
  = Future {
    post(akkaRequest)
  }
}


trait AkkaNonImplementPostMethod extends AkkaPostMethod {
  def post(akkaRequest : AkkaRequest) : HttpResponse = throw new NotImplementedError(s"POST ${ akkaRequest.endPointPrefix } not supported.")
}

trait AkkaNonImplementGetMethod extends AkkaGetMethod {
  def get(akkaRequest : AkkaRequest) : HttpResponse = throw new NotImplementedError(s"GET ${ akkaRequest.endPointPrefix } not supported.")
}

trait AkkaGetPostMethod {
  val akkaPost : AkkaPostMethod
  val akkaGet : AkkaGetMethod
}

class AkkaServerDefinition(
  val serviceModel : ServiceBaseModel,
  akkaGetPostMethod : AkkaGetPostMethod)
  extends AkkHttpServerContracts {

  def requestHandler : HttpRequest => Future[HttpResponse] = {
    case HttpRequest(HttpMethods.POST, uri @ Uri.Path(s"/api/$endPointPrefixes"), seqHeaders, entity, _) =>
      lazy val akkaRequest = AkkaRequest(endPointPrefixes, uri, seqHeaders, entity)
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
