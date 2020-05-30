package shared.com.ortb.serverAkka.framework.sampleCodes

import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import shared.com.ortb.constants.AppConstants
import shared.com.ortb.controllers.traits.AdditionalConfigBasedLogger
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config.core.ServiceBaseModel
import shared.com.ortb.model.config.{ ConfigModel, ServerInfoModel }
import shared.com.ortb.serverAkka.framework.ServerRun
import shared.com.ortb.serverAkka.framework.restClient.softler.context.{ AkkaHttpContext, AkkaHttpRequest, AkkaHttpResponse, AkkaHttpResponseHandler }
import shared.io.extensions.TypeConvertExtensions._
import shared.io.loggers.AppLogger

import scala.concurrent.Future

class AkkaServerDefinition(serviceModel : ServiceBaseModel)
  extends AkkaHttpRequest
    with AkkaHttpResponse
    with AkkaHttpResponseHandler
    with AkkaHttpContext
    with AdditionalConfigBasedLogger
    with ServerRun {

  lazy val appManager : AppManager = AppConstants.AppManager
  lazy val config : ConfigModel = appManager.config
  lazy val serverConfig : ServerInfoModel = config.server
  lazy val endPointPrefixes : String = serviceModel.prefixRouting

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
