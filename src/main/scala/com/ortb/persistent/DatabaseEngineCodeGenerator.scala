package com.ortb.persistent

import com.ortb.manager.AppManager

object DatabaseEngineCodeGenerator extends App {
  val appManager = new AppManager
  val config = appManager.config
  val databaseGenerateConfig = config.databaseGenerate

  slick.codegen.SourceCodeGenerator.run(
    profile = databaseGenerateConfig.profile,// "slick.jdbc.SQLiteProfile",
    jdbcDriver = databaseGenerateConfig.jdbcDriver, //"org.sqlite.JDBC",
    url = databaseGenerateConfig.compiledDatabaseUrl, // "jdbc:sqlite:D:\\PersonalWork\\Github\\scala-rtb-example\\src\\main\\resources\\openRTBSample.db",
    outputDir = databaseGenerateConfig.compiledOutputDir, // "D:\\PersonalWork\\Github\\scala-rtb-example\\src\\main\\scala\\com\\ortb\\persistent\\schema",
    pkg = databaseGenerateConfig.pkg,
    user = None,
    password = None,
    ignoreInvalidDefaults = true,
    outputToMultipleFiles = false
  )
}
