package shared.com.ortb.executors

import shared.com.ortb.manager.AppManager
import shared.com.ortb.manager.traits.DefaultExecutionContextManager
import slick.codegen.SourceCodeGenerator
import slick.jdbc.meta.MTable

import scala.concurrent.duration.Duration
import scala.concurrent.{ Await, Future }

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
  val future : Future[SourceCodeGenerator] = model.map(model => new SourceCodeGenerator(model))
  val codegen : SourceCodeGenerator = Await.result(future, Duration.Inf)
  codegen.writeToFile(databaseProfile, outputDir, compilingPackage, "Tables", "Tables.scala")
  //  slick.codegen.SourceCodeGenerator.run(
  //    profile = ,
  //    jdbcDriver =
  //    url = , //
  //
  //    outputDir = databaseGenerateConfig.compiledOutputDir, //
  //
  //    pkg = databaseGenerateConfig.pkg,
  //    user = None,
  //    password = None,
  //    ignoreInvalidDefaults = true,
  //    outputToMultipleFiles = false
  //    )
}
