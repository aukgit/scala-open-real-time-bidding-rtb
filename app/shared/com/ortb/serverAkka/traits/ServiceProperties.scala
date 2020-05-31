package shared.com.ortb.serverAkka.traits

import akka.http.scaladsl.model.{ HttpRequest, HttpResponse }
import shared.com.ortb.constants.AppConstants
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config.core.ServiceBaseModel
import shared.com.ortb.model.config.{ ConfigModel, ServerInfoModel, ServicesModel }
import shared.com.ortb.serverAkka.traits.akkaMethods.AkkaRequestHandlerGetPostMethods
import shared.io.extensions.TypeConvertExtensions._

import scala.concurrent.Future

trait ServiceProperties {
  lazy val appManager : AppManager = AppConstants.AppManager
  lazy val config : ConfigModel = appManager.config
  lazy val serverConfig : ServerInfoModel = config.server
  lazy val services : ServicesModel = serverConfig.services
  lazy val endPointPrefixes : String = serviceModel.prefixRouting
  lazy val globalDomainHost : String = serverConfig.globalDomainHost
  lazy val servicePort : Int = serviceModel.port
  lazy val finalDomainHost : String = serviceModel
    .domainHost
    .getOrElseDefault(globalDomainHost)

  val serviceModel : ServiceBaseModel
}


