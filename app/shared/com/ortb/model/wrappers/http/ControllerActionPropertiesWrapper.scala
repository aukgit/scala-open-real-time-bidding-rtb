package shared.com.ortb.model.wrappers.http

import shared.com.ortb.enumeration.HttpActionWrapperType.HttpActionWrapperType
import shared.com.ortb.enumeration.HttpMethodType.HttpMethodType
import shared.com.ortb.enumeration._

case class ControllerActionPropertiesWrapper(
  databaseActionType : DatabaseActionType = DatabaseActionType.Unknown,
  httpMethodType : HttpMethodType = HttpMethodType.Get,
  successHttpActionWrapperType : HttpActionWrapperType = HttpActionWrapperType.GenericOkay,
  failedHttpActionWrapperType : HttpActionWrapperType = HttpActionWrapperType.GenericFailed
)
