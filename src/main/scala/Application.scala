import com.ortb.constants.AppConstants
import com.ortb.manager.AppManager
import com.ortb.persistent.DatabaseEngineManager
import com.ortb.persistent.repositories.CampaignRepository

import scala.concurrent.Await
import scala.concurrent.duration.Duration
//import com.ortb.persistent.repositories.CampaignRepository
import com.ortb.persistent.schema.Tables.{CampaignRow, Campaign}
import io.AppLogger
import io.sentry.Sentry
import io.circe.generic.auto._
import io.circe.syntax._
import slick.lifted.{TableQuery, Query}
import slick.profile._
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.{Future, ExecutionContextExecutor}
import scala.concurrent


object Application {
  def main (args : Array[String]) : Unit = {
    val appManager = new AppManager()

    AppLogger.info("Help", isPrintStack = true)
    println(appManager.config.asJson.spaces2);

    val repository = new CampaignRepository(appManager)
    // repository.getAllAsync

    repository.getById(1)
    val x = CampaignRow(
      campaignid = -1,
      campaignname = "Second Cricket Campaign",
      contentcategoryid = "IAB17",
      totalbudgetcpm = 10.0,
      spendalready = 0.0,
      remainingamount = 10.0,
      startdate = Some(0.0), enddate = Some(0.0),
      impressioncount = 0,
      demandsideplatformid = 1,
      isrunning = 1,
      priority = 999,
      isretricttousergender = 0,
      expectedusergender = Some(""),
      publisherid = Some(1))

    var response = repository.add(x)

    println(response)
    var id = repository.db.run{
       repository.getAddAction(x)
    }

    println(repository.toRegular(id))

    repository.getAll

//    import scala.reflect.runtime.universe._
//    val m = typeOf[List[Int]]
//    println(m.getClass.getName)
//    println(typeOf[List[Int]])

//    val dbEngine = appManager.databaseEngine
//    val campaigns = dbEngine.databaseSchema
//      .campaigns
//
//    val q = for {
//      c <- campaigns
//    } yield c;
//
//    val res = dbEngine.db.run(q.result)
//    implicit val defaultExecutionContext: ExecutionContextExecutor = appManager.executionContextManager.createDefault()
//    res.foreach(println)

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
