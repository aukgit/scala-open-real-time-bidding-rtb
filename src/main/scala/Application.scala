import com.ortb.constants.AppConstants
import com.ortb.manager.AppManager
import com.ortb.persistent.DatabaseEngine
import com.ortb.persistent.schema.Tables.{Campaign, CampaignRow}
import io.AppLogger
import io.sentry.Sentry
import io.circe.generic.auto._
import io.circe.syntax._
import slick.lifted.{TableQuery, Query}
import slick.profile._
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.Future

object Application {
  def main(args: Array[String]): Unit = {
    val appManager = new AppManager()

    AppLogger.info("Help", isPrintStack = true)
    println(appManager.config.asJson.noSpaces);
    val dbEngine = new DatabaseEngine(appManager)
    val db = dbEngine.db
    val campaigns = dbEngine.databaseSchemas
      .campaigns

    def sampleTest: Future[Seq[CampaignRow]] = {
      val query = for {
        c <- campaigns
      } yield (c)

      query.result.statements.foreach(println)
      db.run(query.result)
    }

    sampleTest

//    val campaigns = TableQuery[Campaign].filter(c => c.campaignid >= 0)
//
//    campaigns.result.statements.foreach(println)
//    engine.db.run()

//    val decodedFoo = decode[Foo](json)
//    println(decodedFoo)

//    val c = new Configuration();
//    val config = c.getConfig("configuration.json")

//    try {
//      throw new Exception("Wait a little XX")
//    }
//    catch {
//      case e: Exception =>
//        AppLogger.error(e)
//    }
  }
}
