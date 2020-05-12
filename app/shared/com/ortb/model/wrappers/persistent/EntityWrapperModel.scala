package shared.com.ortb.model.wrappers.persistent

case class EntityWrapperModel[TRow, TKey](entityId : TKey, entity : TRow)


