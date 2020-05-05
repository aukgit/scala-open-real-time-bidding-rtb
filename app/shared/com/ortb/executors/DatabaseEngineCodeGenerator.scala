package shared.com.ortb.executors

import shared.com.ortb.manager.AppManager
import shared.com.ortb.manager.traits.DefaultExecutionContextManager
import slick.codegen.SourceCodeGenerator
import slick.driver
import slick.jdbc.JdbcProfile
import slick.jdbc.meta.MTable

import scala.concurrent.duration.Duration
import scala.concurrent.{ Await, Future }
import slick.{model => m}

class CustomSourceCodeGenerator(model: m.Model) extends SourceCodeGenerator(model) {

  // add some custom imports
  // TODO: fix these imports to refer to your JdbcSupport and your Joda imports
  override def code = "import com.github.tototoshi.slick.PostgresJodaSupport._\n" + "import org.joda.time.DateTime\n" + super.code

  override def Table = new Table(_) {
    override def Column = new Column(_) {

      // munge rawType -> SQL column type HERE (scaladoc in Slick 2.1.0 is outdated or incorrect, GeneratorHelpers#mapJdbcTypeString does not exist)
      // you can filter on model.name for the column name or model.tpe for the column type
      // your IDE won't like the String here but don't worry, the return type the compiler expects here is String
      override def rawType : String = model.tpe match {
        case "java.sql.Timestamp"               => "DateTime" // kill j.s.Timestamp
        case _ => {
          //          println(s"${model.table.table}#${model.name} tpe=${model.tpe} rawType=${super.rawType}")
          super.rawType
        }
      }
    }
  }
}

object DatabaseEngineCodeGenerator extends App with DefaultExecutionContextManager {
  lazy val appManager = new AppManager
  lazy val config = appManager.config
  lazy val databaseGenerateConfig = config.databaseGenerate
  lazy val databaseProfile = databaseGenerateConfig.profile // "slick.jdbc.SQLiteProfile"
  lazy val jdbcDriver = databaseGenerateConfig.jdbcDriver // "org.sqlite.JDBC",
  // "jdbc:sqlite:D:\\PersonalWork\\Github\\scala-rtb-example\\src\\main\\resources\\openRTBSample.db",
  lazy val url = databaseGenerateConfig.compiledDatabaseUrl // Database location path
  // "D:\\PersonalWork\\Github\\scala-rtb-example\\src\\main\\scala\\com\\ortb\\persistent\\schema",
  lazy val outputDir = databaseGenerateConfig.compiledOutputDir
  lazy val compilingPackage = databaseGenerateConfig.pkg
  lazy val db = appManager.getDb
  lazy val mTables = MTable.getTables(
    None,
    None,
    None,
    Some(Seq("TABLE", "VIEW")))

  val dbio = slick.jdbc.SQLiteProfile.createModel(
    Some(mTables))

  val model = db.run(dbio)
  val eventualSourceCodeGenerator : Future[SourceCodeGenerator] = model.map(model => new CustomSourceCodeGenerator(model))
  val codegen : SourceCodeGenerator = Await.result(eventualSourceCodeGenerator, Duration.Inf)

  codegen.writeToFile(
    databaseProfile,
    outputDir,
    compilingPackage,
    "Tables",
    "Tables.scala")
}
