package io

import java.nio.file.{Files, Path, Paths}
import java.util

import io.traits.file.ExecuteInputOutputAction

import scala.io.{BufferedSource, Source}


class FileReader extends ExecuteInputOutputAction {
  override def executeInputOutputAction[ReturnType](
    path: Path,
    performingAction: () => ReturnType,
    emptyResult: ReturnType
  ): ReturnType = {
    try {
      if (Files.exists(path)) {
        return performingAction()
      }
    }
    catch {
      case e: Exception =>
        AppLogger.error(e)
    }

    AppLogger.debug(s"${path} doesn't exist or cannot (something went wrong) read from file system.")
    return emptyResult
  }

  /**
   * return given path's content if file exist or else returns empty string ("").
   *
   * @param path : file system path
   *
   * @return content in string format.
   */
  def getContents(path: String): String = {
    val givenPath = Paths.get(path)

    def action() = Files.readString(givenPath)

    executeInputOutputAction(givenPath, action, "")
  }

  /**
   * return buffered source using Source.fromFile
   *
   * @param path : file system path
   *
   * @return content in string format.
   */
  def getBufferedSource(path: String): BufferedSource = {
    val givenPath = Paths.get(path)

    def action() = Source.fromFile(path)

    executeInputOutputAction(givenPath, action, null)
  }

  /**
   * return given path's content if file exist or else returns null.
   *
   * @param path : file system path
   *
   * @return contents in Java List
   */
  def getLines(path: String): util.List[String] = {
    val givenPath = Paths.get(path)

    def action() = Files.readAllLines(givenPath)

    executeInputOutputAction(givenPath, action, null)
  }
}
