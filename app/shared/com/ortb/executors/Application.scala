package shared.com.ortb.executors

import com.ortb.manager.AppManager

object Application {
  def main(args : Array[String]) : Unit = {
    val appManager = new AppManager
    println(appManager.config)
  }
}
