package shared.com.ortb.webapi.core.traits.implementations

import shared.com.ortb.webapi.core.traits.RestWebApiProperties
import play.api.Logger
import shared.com.ortb.constants.AppConstants
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config.ConfigModel

trait RestWebApiPropertiesImplementation[TTable, TRow, TKey] extends RestWebApiProperties[TTable, TRow, TKey] {
  lazy override val noContentMessage : String = AppConstants.NoContentInRequest
  lazy val appManager : AppManager = service.appManager
  lazy val config : ConfigModel = appManager.config
  lazy protected val logger : Logger = Logger(this.getClass)
}
