package shared.com.ortb.webapi.core

import shared.com.ortb.webapi.core.traits._
import shared.com.ortb.webapi.core.traits.implementations._
import shared.com.ortb.webapi.core.traits.implementations.actions._
import play.api.mvc._
import shared.com.ortb.model.wrappers.http._
import shared.io.loggers.AppLogger

abstract class AbstractRestWebApi[TTable, TRow, TKey](
  val components : ControllerComponents)
  extends AbstractController(components)
    with RestWebApiContracts[TTable, TRow, TKey]
    with RestWebApiHandleErrorImplementation[TTable, TRow, TKey]
    with RestWebApiBodyProcessorImplementation[TTable, TRow, TKey]
    with RestWebApiResponsePerformImplementation[TTable, TRow, TKey]
    with RestWebApiAddActionImplementation[TTable, TRow, TKey]
    with RestWebApiAddOrUpdateActionImplementation[TTable, TRow, TKey]
    with RestWebApiDeleteActionImplementation[TTable, TRow, TKey]
    with RestWebApiUpdateActionImplementation[TTable, TRow, TKey]
    with RestWebApiAddEntitiesActionImplementation[TTable, TRow, TKey]
    with RestWebApiGetByIdActionImplementation[TTable, TRow, TKey]
    with RestWebApiGetAllActionImplementation[TTable, TRow, TKey]
    with RestWebApiMessagesImplementation[TRow]
    with RestWebApiPropertiesImplementation[TTable, TRow, TKey] {

  def getRequestUri(request : Request[AnyContent]) : String = {
    request.uri
  }

  def createHttpFailedActionWrapper(
    message : String,
    actionWrapper : ControllerGenericActionWrapper,
    methodName : String = ""
  ) : HttpFailedActionWrapper[TRow, TKey] = {
    AppLogger.error(s"${ message } $methodName")
    val httpFailedActionWrapper = HttpFailedActionWrapper[TRow, TKey](
      additionalMessage = Some(message),
      methodName = Some(methodName),
      controllerGenericActionWrapper = actionWrapper)

    httpFailedActionWrapper
  }
}
