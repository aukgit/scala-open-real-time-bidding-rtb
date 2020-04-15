package com.ortb.model.persistent

case class EntityWrapper[TRow, TKey](entityId: TKey, entity: TRow)
