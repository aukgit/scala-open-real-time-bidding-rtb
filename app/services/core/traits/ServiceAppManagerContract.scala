package services.core.traits

import shared.com.ortb.manager.AppManager

trait ServiceAppManagerContract[TTable, TRow, TKey] {
  val appManager : AppManager
}
