package shared.io

import shared.com.ortb.manager.AppManager

object Application {
  def main(args: Array[String]): Unit = {
    val appManager = new AppManager
    println(appManager.config)
    println(appManager.config)
  }
}
