package shared.com.ortb.model.wrappers.persistent

case class WebApiEntitiesResponseWrapper[TRow, TKey](
  entityWrapper : Option[Iterable[EntityWrapperWithOptions[TRow, TKey]]],
  bodyRaw       : String
)
