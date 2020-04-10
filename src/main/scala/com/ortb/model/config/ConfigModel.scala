package com.ortb.model.config

case class ConfigModel(
  applicationName: String,
  displayVersion: String,
  server: ServerInfoModel,
  isDebug: Boolean,
  isLogError: Boolean,
  author: String,
  databaseRelativePath: String
)
