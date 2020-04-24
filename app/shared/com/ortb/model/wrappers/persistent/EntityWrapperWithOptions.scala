package shared.com.ortb.model.wrappers.persistent

case class EntityWrapperWithOptions[TRow, TKey](
  entityId: Option[TKey] = None,
  entity: Option[TRow] = None)
