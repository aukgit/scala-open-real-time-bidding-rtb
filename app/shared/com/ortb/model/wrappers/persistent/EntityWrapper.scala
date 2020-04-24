package shared.com.ortb.model.wrappers.persistent

case class EntityWrapper[TRow, TKey](entityId: TKey, entity: TRow)


