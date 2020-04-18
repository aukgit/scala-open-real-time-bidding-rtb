package shared.com.ortb.webapi.traits

import shared.com.ortb.persistent.repositories.pattern.traits.adapters.RepositoryJsonAdapter

trait RestWebApiJson[TTable, TRow, TKey]
  extends RepositoryJsonAdapter[TTable, TRow, TKey]
