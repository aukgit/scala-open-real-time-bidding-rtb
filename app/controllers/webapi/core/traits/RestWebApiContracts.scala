package controllers.webapi.core.traits

import shared.com.ortb.webapi.traits._
import shared.io.jsonParse.traits.CirceJsonSupport

trait RestWebApiContracts[TTable, TRow, TKey]
  extends
    RestWebApi[TTable, TRow, TKey] with
    RestWebApiBodyProcessor[TTable, TRow, TKey] with
    RestWebApiMessages[TTable, TRow, TKey] with
    CirceJsonSupport with
    ServiceContract[TTable, TRow, TKey]
