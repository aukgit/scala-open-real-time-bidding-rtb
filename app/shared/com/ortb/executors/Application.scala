package shared.com.ortb.executors

import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.Repositories
import shared.com.ortb.persistent.schema.Tables._
import shared.io.loggers.AppLogger

object Application {
  def main(args: Array[String]): Unit = {
    val appManager = new AppManager
    println(appManager.config)
    println(appManager.config)

    val repos = new Repositories(appManager)

    val row = BidresponseRow(-1)
    val response = repos
      .bidResponseRepository
      .add(row)
    val json = response.getAsJson()()
    AppLogger.debug(json)
  }
}
