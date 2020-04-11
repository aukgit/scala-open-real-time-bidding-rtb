package com.ortb.constants

import java.io.File

sealed class PathConstants {
  val WorkingDirectory: String = new java.io.File(AppConstants.Dot).getCanonicalPath

  val DirectorySeparator: String = File.separator
  val GenericPathSeparator: String = "*:"

  val ResourcePath: String = new File    (ClassLoader
      .getSystemResource(AppConstants.Dot)
      .getPath).getAbsolutePath.toString

  val ConfigDefaultPath: String =
    s"${ResourcePath}${DirectorySeparator}${AppConstants.DefaultConfigFileNameWithExtension}"
}
