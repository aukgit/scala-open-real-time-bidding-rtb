package controllers.webapi.core

import shared.com.ortb.webapi.traits._

trait RestWebApiContracts[TTable, TRow, TKey]
  extends
    RestWebApi[TTable, TRow, TKey] with
    RestWebApiBodyProcessor[TTable, TRow, TKey] with
    RestWebApiMessages[TTable, TRow, TKey] with
    RestWebApiJson[TTable, TRow, TKey] with
    RestWebApiEntityJsonAdapter[TTable, TRow, TKey]
