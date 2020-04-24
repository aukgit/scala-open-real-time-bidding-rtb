package shared.com.ortb.model.attributes

import shared.com.ortb.enumeration.DatabaseActionType.DatabaseActionType

case class GenericResponseAttributesModel(
  isSuccess : Boolean,
  actionType : Option[DatabaseActionType],
  requestUri : String = "",
  message         : String = "")
