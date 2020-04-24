package controllers.webapi.core.traits

import shared.com.ortb.constants.AppConstants
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config.ConfigModel
import shared.com.ortb.webapi.traits._
import shared.io.jsonParse.traits.CirceJsonSupport

trait RestWebApiContracts[TTable, TRow, TKey]
  extends
    RestWebApi[TTable, TRow, TKey] with
    RestWebApiBodyProcessor[TTable, TRow, TKey] with
    RestWebApiMessages[TTable, TRow, TKey] with
    CirceJsonSupport with
    WebApiServiceContract[TTable, TRow, TKey] with
    RestWebApiProperties[TTable, TRow, TKey] with
    RestWebApiPropertiesImplementation[TTable, TRow, TKey]
