package shared.com.ortb.webapi.core.traits

import shared.com.ortb.webapi.core.traits.actions.RestWebApiActionsContracts
import shared.io.jsonParse.traits.CirceJsonSupport

trait RestWebApiContracts[TTable, TRow, TKey]
  extends
    RestWebApi[TTable, TRow, TKey]
    with RestWebApiBodyProcessor[TTable, TRow, TKey]
    with RestWebApiMessages[TRow]
    with CirceJsonSupport
    with RestWebApiResponsePerform[TTable, TRow, TKey]
    with WebApiServiceContract[TTable, TRow, TKey]
    with RestWebApiProperties[TTable, TRow, TKey]
    with RestWebApiActionsContracts[TTable, TRow, TKey]
