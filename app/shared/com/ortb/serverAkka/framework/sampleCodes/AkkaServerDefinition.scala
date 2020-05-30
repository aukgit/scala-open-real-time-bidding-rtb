package shared.com.ortb.serverAkka.framework.sampleCodes

import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import shared.com.ortb.model.config.core.ServiceBaseModel
import shared.io.extensions.TypeConvertExtensions._

import scala.concurrent.Future

class AkkaServerDefinition(val serviceModel : ServiceBaseModel)
  extends AkkHttpServerContracts {

  override def serverRunAt(port : Int = serviceModel.port) : Unit = {
    val domain = serviceModel.domain.getOrElse(serverConfig.commonDomain)
    val portSelected = port.getOnZeroOrNegative(serviceModel.port)

    log(s"Server Starting (Title : ${ serviceModel.title }, Description: ${ serviceModel.description })", s"Domain: $domain, Port: $portSelected, ")
    log("prefixRouting", endPointPrefixes)
    Http().bindAndHandleAsync(requestHandler, domain, portSelected)
  }

  def requestHandler : HttpRequest => Future[HttpResponse] = {
    case HttpRequest(HttpMethods.POST, uri @ Uri.Path(s"/api/$endPointPrefixes"), seqHeaders, entity, _) =>
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
