package controllers.webapi.core.traits

import services.core.AbstractBasicPersistentService

trait ServiceContract[TTable, TRow, TKey] {
  val service : AbstractBasicPersistentService[TTable, TRow, TKey]
}
