package com.ortb.model.config

case class DatabaseGenerateConfigModel(
  profile                 : String,
  jdbcDriver              : String,
  databaseUrl             : String,
  var compiledDatabaseUrl : String,
  outputDir               : String,
  var compiledOutputDir   : String,
  pkg                     : String
)
