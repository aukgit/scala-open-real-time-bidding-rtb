package controllers.webapi.core.traits

import controllers.webapi.core.traits.implementations.actions.RestWebApiPerformActionImplementation
import controllers.webapi.core.traits.implementations.{ RestWebApiBodyProcessorImplementation, RestWebApiPropertiesImplementation }
import controllers.webapi.core.{ RestWebApiMessages, RestWebApiPerformAction }
import shared.com.ortb.webapi.traits._
import shared.io.jsonParse.traits.CirceJsonSupport

trait RestWebApiContracts[TTable, TRow, TKey]
  extends
    RestWebApi[TTable, TRow, TKey] with
    RestWebApiBodyProcessor[TTable, TRow, TKey] with
    RestWebApiMessages[TTable, TRow, TKey] with
    CirceJsonSupport with
    RestWebApiPerformAction[TTable, TRow, TKey] with
    WebApiServiceContract[TTable, TRow, TKey] with
    RestWebApiProperties[TTable, TRow, TKey]

trait RestWebApiContractsImplementation[TTable, TRow, TKey]
  extends
    RestWebApiContracts[TTable, TRow, TKey] with
    RestWebApiBodyProcessorImplementation[TTable, TRow, TKey] with
    RestWebApiPropertiesImplementation[TTable, TRow, TKey] with
    RestWebApiPerformActionImplementation[TTable, TRow, TKey]

