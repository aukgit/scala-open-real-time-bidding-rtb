package shared.com.ortb.model.wrappers.persistent

case class WebApiEntityResponseWrapper[TRow, TKey](
  entityWrapper : Option[EntityWrapperWithOptions[TRow, TKey]],
  bodyRaw : String
)
