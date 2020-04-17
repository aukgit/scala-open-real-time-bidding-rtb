package shared.com.ortb.model.wrappers.persistent

case class WebApiEntityResponseWrapper[TRow, TKey](
  entityWrapper : Option[EntityWrapper[TRow, TKey]],
  bodyRaw      : String
)


