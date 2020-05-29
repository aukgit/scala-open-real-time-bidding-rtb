package shared.com.ortb.manager.traits

import shared.com.ortb.model.config.ConfigModel

trait ConfigurationManager {
  def getConfig(path : String) : ConfigModel
}
