package shared.com.ortb.model.attributes

import shared.com.ortb.constants.AppConstants
import shared.com.ortb.enumeration.DatabaseActionType.DatabaseActionType

case class GenericResponseAttributesModel(
  isSuccess : Boolean,
  id : Option[String] = AppConstants.EmptyStringOption,
  ids : Option[List[String]] = None,
  actionType : Option[DatabaseActionType],
  requestUri : String = "",
  message : String = "")
