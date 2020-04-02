package io

import java.nio.file.{Files, Paths}
import java.util

import io.traits.ExecuteInputOutputAction


class FileReader extends ExecuteInputOutputAction {
  override def executeInputOutputAction[ReturnType](
    path: String,
    performingAction: () => ReturnType,
    emptyResult: ReturnType
  ): ReturnType = {
    try {
      val givenPath = Paths.get(path);
      if (Files.exists(givenPath)) {
        return performingAction();
      }
    }
    catch {
      case e: Exception =>
        AppLogger.error(e);
    }

    AppLogger.debug(s"${path} doesn't exist or cannot (something went wrong) read from file system.");
    return emptyResult;
  }

  /**
   * return given path's content if file exist or else returns empty string ("").
   *
   * @param path : file system path
   *
   * @return content in string format.
   */
  def getContents(path: String): String = {
    val pgiv
    def action() {
      return Files.readAllLines()
    }

    executeInputOutputAction(path, )
  }

  /**
   * return given path's content if file exist or else returns null.
   *
   * @param path : file system path
   *
   * @return contents in Java List
   */
  def getLines(path: String): util.List[String] = {
    try {
      val givenPath = Paths.get(path);
      if (Files.exists(givenPath)) {
        return Files.readAllLines(givenPath);
      }
    }
    catch {
      case e: Exception =>
        AppLogger.error(e);
    }

    AppLogger.debug(s"${path} doesn't exist or cannot read from file system.");
    return null;
  }

}
