package shared.com.ortb.model.repository

import shared.com.ortb.enumeration.DatabaseActionType.DatabaseActionType

case class GenericResponseAttributesModel(
  isSuccess : Boolean,
  actionType : Option[DatabaseActionType],
  message    : String = null)
