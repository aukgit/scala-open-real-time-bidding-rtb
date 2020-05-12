package controllers.webapi.core.traits

import play.api.Logger
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config.ConfigModel

trait RestWebApiProperties[TTable, TRow, TKey] extends WebApiServiceContract[TTable, TRow, TKey]{
 val noContentMessage : String
 val appManager : AppManager
 val config : ConfigModel
 protected val logger : Logger
}
