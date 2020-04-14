import com.ortb.enumeration.DatabaseActionType
import com.ortb.manager.AppManager
import com.ortb.persistent.repositories.CampaignRepository
import com.ortb.persistent.schema.Tables.CampaignRow
import io.AppLogger
import slick.dbio.Effect
import slick.jdbc.SQLiteProfile.api._
import slick.sql.FixedSqlAction

object Application {
  def main(args : Array[String]) : Unit = {
    val appManager = new AppManager

    AppLogger.info("Help", isPrintStack = true)
    // println(appManager.config.asJson.spaces2);

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

    val response = repository.addEntities(x, 50)

    println(response)
//    var id = repository.db.run{
//       repository.getAddAction(x)
//    }
//
//    println(repository.toRegular(id))
//    repository.delete(3)
    repository.getAll
    val removeall : FixedSqlAction[Int, NoStream, Effect.Write] = repository.table.filter(w=> w.campaignid > 2).delete

    val rs5 = repository.quickSave(
      removeall,
      DatabaseActionType.Delete
      )

    println(rs5)

    val l = repository.getAll

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
//    implicit val defaultExecutionContext: ExecutionContextExecutor = appManager.executionContextManager
//    .createDefault()
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
