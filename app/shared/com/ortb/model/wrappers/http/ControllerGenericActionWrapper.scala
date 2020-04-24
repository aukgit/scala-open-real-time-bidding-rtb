package shared.com.ortb.model.wrappers.http

import play.api.mvc.{ AnyContent, Request }
import shared.com.ortb.enumeration.DatabaseActionType.DatabaseActionType
import shared.com.ortb.enumeration.HttpActionWrapperType.HttpActionWrapperType
import shared.com.ortb.enumeration.HttpMethodType.HttpMethodType
import shared.com.ortb.enumeration.{ HttpActionWrapperType, HttpMethodType }

case class ControllerGenericActionWrapper(
  httpMethodType : Option[HttpMethodType],
  var successHttpActionWrapperType : HttpActionWrapperType = HttpActionWrapperType.GenericOkay,
  var failedHttpActionWrapperType : HttpActionWrapperType = HttpActionWrapperType.GenericFailed,
  databaseActionType : Option[DatabaseActionType],
  rawBodyRequest : Option[Request[AnyContent]] = None
) {
  httpMethodType.get match {
    case HttpMethodType.Get =>
      successHttpActionWrapperType = HttpActionWrapperType.GetOk
      failedHttpActionWrapperType = HttpActionWrapperType.GetFailed
    case HttpMethodType.Post =>
      successHttpActionWrapperType = HttpActionWrapperType.PostAddOk
      failedHttpActionWrapperType = HttpActionWrapperType.PostAddFailed
    case HttpMethodType.Put =>
      successHttpActionWrapperType = HttpActionWrapperType.PutUpdateOk
      failedHttpActionWrapperType = HttpActionWrapperType.PutUpdateFailed
    case HttpMethodType.Delete =>
      successHttpActionWrapperType = HttpActionWrapperType.DeleteOk
      failedHttpActionWrapperType = HttpActionWrapperType.DeleteFailed
    case HttpMethodType.Patch =>
      successHttpActionWrapperType = HttpActionWrapperType.PatchOk
      failedHttpActionWrapperType = HttpActionWrapperType.PatchFailed
    case _ => throw new NotImplementedError()
  }
}
