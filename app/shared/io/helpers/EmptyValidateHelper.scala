package shared.io.helpers

import shared.com.ortb.constants.AppConstants
import shared.io.loggers.AppLogger

object EmptyValidateHelper {

  def isDefined[A](
      item: Option[A],
      message: Option[String] = Some(AppConstants.NoContent)): Boolean = {
    val hasItem = item != null
    item.isDefined &&
    item.get != null &&
    item.get != None

    val hasMessage = message != null &&
      message.isDefined &&
      !message.get.isEmpty

    if (!hasItem && hasMessage) {
      AppLogger.debug(message.get)
    }

    hasItem
  }

  //noinspection DuplicatedCode
  def isEmpty[A](
      item: Option[A],
      message: Option[String] = Some(AppConstants.NoContent)): Boolean = {
    !isDefined(item, message)
  }

  def isEmptyOptionString(
      givenString: Option[String],
      message: Option[String] = Some(AppConstants.NoContent)): Boolean = {
    val hasItem = givenString != null &&
      givenString.isDefined &&
      givenString.nonEmpty &&
      !givenString.get.isBlank;

    val hasMessage = message != null &&
      message.isDefined &&
      !message.get.isEmpty

    if (!hasItem && hasMessage) {
      AppLogger.debug(message.get)
    }

    !hasItem
  }

  def isEmptyString(
      givenString: String,
      message: Option[String] = Some(AppConstants.NoContent)): Boolean = {
    val hasItem = givenString != null &&
      givenString.nonEmpty &&
      !givenString.isBlank;

    val hasMessage = message != null &&
      message.isDefined &&
      !message.get.isEmpty

    if (!hasItem && hasMessage) {
      AppLogger.debug(message.get)
    }

    !hasItem
  }

  //noinspection DuplicatedCode
  def isItemsEmpty[A](items: Option[Iterable[A]],
                      message: Option[String] = None): Boolean = {
    val hasItem = items != null &&
      items.isDefined &&
      items.get != null &&
      items.get.nonEmpty

    val hasMessage = message != null &&
      message.isDefined &&
      !message.get.isEmpty

    if (!hasItem && hasMessage) {
      AppLogger.debug(message.get)
    }

    !hasItem
  }
}
