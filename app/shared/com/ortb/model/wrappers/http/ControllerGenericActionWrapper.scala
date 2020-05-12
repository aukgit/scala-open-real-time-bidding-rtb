package shared.com.ortb.model.wrappers.http

import play.api.mvc.{ AnyContent, Request }
import shared.com.ortb.enumeration.ControllerDefaultActionType.ControllerDefaultActionType
import shared.com.ortb.enumeration._

/**
 * Using a matcher it
 *
 * @param isMultipleTransaction true meaning performing addEntities, updateEntities or deleteEntities
 */
case class ControllerGenericActionWrapper(
  controllerDefaultActionType : ControllerDefaultActionType,
  requestContent : Option[Request[AnyContent]] = None,
  isMultipleTransaction : Boolean = false,
  message : String = ""
) {
  /**
   * Using a pattern matcher.
   *
   * @return ControllerActionPropertiesWrapper based on controllerDefaultActionType
   */
  def getControllerActionPropertiesWrapper : ControllerActionPropertiesWrapper = controllerDefaultActionType match {
    case ControllerDefaultActionType.GetOrRead =>
      ControllerActionPropertiesWrapper(
        databaseActionType = DatabaseActionType.Read,
        httpMethodType = HttpMethodType.Get,
        successHttpActionWrapperType = HttpActionWrapperType.GetOk,
        failedHttpActionWrapperType = HttpActionWrapperType.GetFailed)
    case ControllerDefaultActionType.Add | ControllerDefaultActionType.AddMany =>
      ControllerActionPropertiesWrapper(
        databaseActionType = DatabaseActionType.Create,
        httpMethodType = HttpMethodType.Post,
        successHttpActionWrapperType = HttpActionWrapperType.PostAddOk,
        failedHttpActionWrapperType = HttpActionWrapperType.PostAddFailed)

    case ControllerDefaultActionType.Update | ControllerDefaultActionType.UpdateMany =>
      ControllerActionPropertiesWrapper(
        databaseActionType = DatabaseActionType.Update,
        httpMethodType = HttpMethodType.Put,
        successHttpActionWrapperType = HttpActionWrapperType.PutUpdateOk,
        failedHttpActionWrapperType = HttpActionWrapperType.PutUpdateFailed)

    case ControllerDefaultActionType.Delete | ControllerDefaultActionType.DeleteMany =>
      ControllerActionPropertiesWrapper(
        databaseActionType = DatabaseActionType.Delete,
        httpMethodType = HttpMethodType.Delete,
        successHttpActionWrapperType = HttpActionWrapperType.PutUpdateOk,
        failedHttpActionWrapperType = HttpActionWrapperType.PutUpdateFailed)
    case ControllerDefaultActionType.PartialUpdate =>
      ControllerActionPropertiesWrapper(
        databaseActionType = DatabaseActionType.Update,
        httpMethodType = HttpMethodType.Patch,
        successHttpActionWrapperType = HttpActionWrapperType.PatchUpdatePartialOk,
        failedHttpActionWrapperType = HttpActionWrapperType.PatchUpdatePartialFailed)
    case _ => throw new NotImplementedError()
  }
}
