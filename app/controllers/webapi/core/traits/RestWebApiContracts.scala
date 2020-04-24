package controllers.webapi.core.traits

import controllers.webapi.core.{ RestWebApiMessages, RestWebApiResponsePerform }
import shared.io.jsonParse.traits.CirceJsonSupport

trait RestWebApiContracts[TTable, TRow, TKey]
  extends
    RestWebApi[TTable, TRow, TKey] with
    RestWebApiBodyProcessor[TTable, TRow, TKey] with
    RestWebApiMessages[TTable, TRow, TKey] with
    CirceJsonSupport with
    RestWebApiResponsePerform[TTable, TRow, TKey] with
    WebApiServiceContract[TTable, TRow, TKey] with
    RestWebApiProperties[TTable, TRow, TKey] {}
