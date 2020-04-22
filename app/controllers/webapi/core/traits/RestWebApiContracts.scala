package controllers.webapi.core.traits

import shared.com.ortb.implicits.implementations.CirceJsonSupport
import shared.com.ortb.webapi.traits._

trait RestWebApiContracts[TTable, TRow, TKey]
  extends
    RestWebApi[TTable, TRow, TKey] with
    RestWebApiBodyProcessor[TTable, TRow, TKey] with
    RestWebApiMessages[TTable, TRow, TKey] with
    CirceJsonSupport with
    ServiceContract[TTable, TRow, TKey]
