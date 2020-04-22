package controllers.webapi.core

import services.core.AbstractBasicPersistentService

trait ServiceContract[TTable, TRow, TKey] {
  val service : AbstractBasicPersistentService[TTable, TRow, TKey]
}
