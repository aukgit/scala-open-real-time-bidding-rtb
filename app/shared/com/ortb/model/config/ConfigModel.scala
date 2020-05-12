package shared.com.ortb.model.config

case class ConfigModel(
  applicationName : String,
  displayVersion : String,
  server : ServerInfoModel,
  isDebug : Boolean,
  isLogError : Boolean,
  isLogDatabaseQueryLogs : Boolean,
  isLogDatabaseActionsToDatabase : Boolean,
  author : String,
  databaseRelativePath : String,
  databaseGenerate : DatabaseGenerateConfigModel,
  defaultTimeout : Int,
  defaultParallelProcessing : Int,
  isEnableSentry : Boolean
)
