package shared.io.helpers

import shared.com.ortb.constants.AppConstants
import shared.io.loggers.AppLogger

object EmptyValidateHelper {
  def throwOnNullOrNone(
    item : Any,
    message : Option[String] = None) : Unit = {
    val isEmpty = item == null ||
      String.valueOf(item) == "None" ||
      isEmptyAny(item, None)

    if (isEmpty && isOptionStringDefinedWithoutMessage(message)) {
      throw new NullPointerException(message.get)
    }

    if (isEmpty) {
      throw new NullPointerException
    }
  }

  def isEmptyAny(
    item : Any,
    message : Option[String] = Some(AppConstants.NoContent)) : Boolean = {
    val isDirectEmpty = item == null || String.valueOf(item) == "None"
    if (isDirectEmpty) {
      return true
    }

    return item match {
      case someString : Option[String] => return EmptyValidateHelper.isEmptyOptionString(someString, message)
      case someIterable : Option[Iterable[_]] => return EmptyValidateHelper.isItemsEmpty(someIterable, message)
      case asString : String => return EmptyValidateHelper.isEmptyString(asString, message)
      case asSome : Option[_] => return EmptyValidateHelper.isEmpty(asSome, message)
      case asEither : Either[_, _] => return EmptyValidateHelper.isRightEmptyOnEither(asEither, message)
      case asAny => return EmptyValidateHelper.isEmpty(Some(asAny), message)
    }
  }

  def isRightEmptyOnEither[A, B](
    item : Either[A, B],
    message : Option[String] = Some(AppConstants.NoContent)) : Boolean = {
    !isRightDefinedOnEither(item, message)
  }

  def isRightDefinedOnEither[A, B](
    item : Either[A, B],
    message : Option[String] = Some(AppConstants.NoContent)) : Boolean = {
    val hasItem = item != null && item.getOrElse(null) != null

    //noinspection DuplicatedCode
    val hasMessage = isOptionStringDefinedWithoutMessage(message)

    if (!hasItem && hasMessage) {
      AppLogger.debug(message.get)
    }

    hasItem
  }

  //noinspection DuplicatedCode
  def isEmpty[A](
    item : Option[A],
    message : Option[String] = Some(AppConstants.NoContent)) : Boolean = {
    !isDefined(item, message)
  }

  //noinspection DuplicatedCode
  def isEmptyDirect[A](
    item : A,
    message : Option[String] = Some(AppConstants.NoContent)) : Boolean = {
    !isDefined(Some(item), message)
  }

  /**
   * Returns true of the string is None, null or "" or has emptySpaces("   ")
   *
   * @param givenString
   * @param message
   *
   * @return
   */
  def isEmptyOptionString(
    givenString : Option[String],
    message : Option[String] = Some(AppConstants.NoContent)) : Boolean = {
    !isOptionStringDefined(givenString, message)
  }

  /**
   * Returns true of the has at least a valid character expect empty(" ")
   * Writes to message if message given and not empty.
   *
   * @param givenString
   * @param message
   *
   * @return
   */
  def isOptionStringDefined(
    givenString : Option[String],
    message : Option[String] = None) : Boolean = {
    val hasItem = isOptionStringDefinedWithoutMessage(givenString)

    //noinspection DuplicatedCode
    val hasMessage = isOptionStringDefinedWithoutMessage(message)

    if (!hasItem && hasMessage) {
      AppLogger.debug(message.get)
    }

    hasItem
  }

  /**
   * Returns true if string is null, "", or even "  " empty spaces.
   * Writes to message if message given and not empty.
   *
   * @param givenString
   * @param message
   *
   * @return
   */
  def isEmptyString(
    givenString : String,
    message : Option[String] = Some(AppConstants.NoContent)) : Boolean = {
    !isStringDefined(givenString, message)
  }

  /**
   * Returns true of the has at least a valid character expect empty(" ")
   *
   * @param givenString
   * @param message
   *
   * @return
   */
  def isStringDefined(
    givenString : String,
    message : Option[String] = None) : Boolean = {
    val hasItem = givenString != null &&
      givenString.nonEmpty &&
      !givenString.isBlank

    //noinspection DuplicatedCode
    val hasMessage = isOptionStringDefinedWithoutMessage(message)

    if (!hasItem && hasMessage) {
      AppLogger.debug(message.get)
    }

    hasItem
  }

  /**
   * Returns true if only all are defined or else returns false.
   *
   * @param items
   * @tparam A
   *
   * @return
   */
  def isAllDefinedFromMultiple[A](
    items : Option[A]*) : Boolean = {
    if (isItemsEmpty(Some(items))) {
      return false
    }

    items.forall(item => isDefined(item))
  }

  def isDefinedDirect[A](
    item : A,
    message : Option[String] = Some(AppConstants.NoContent)) : Boolean = {
    isDefined(Some(item), message)
  }

  def isDefined[A](
    item : Option[A],
    message : Option[String] = Some(AppConstants.NoContent)) : Boolean = {
    val hasItem = item != null &&
      item.isDefined &&
      item.get != null &&
      item.get != Nil &&
      item.get != None

    //noinspection DuplicatedCode
    val hasMessage = isOptionStringDefinedWithoutMessage(message)

    if (!hasItem && hasMessage) {
      AppLogger.debug(message.get)
    }

    hasItem
  }

  //noinspection DuplicatedCode
  def isItemsEmptyArray[A](
    items : Option[Array[A]],
    message : Option[String] = None) : Boolean = {
    items == null ||
      items.isEmpty ||
      !hasAnyItemDirect(items.get, message)
  }

  //noinspection DuplicatedCode
  def isItemsEmpty[A](
    items : Option[Iterable[A]],
    message : Option[String] = None) : Boolean = {
    !hasAnyItem(items, message)
  }

  /**
   * Returns true if only all are empty or else returns false.
   *
   * @param items
   * @tparam A
   *
   * @return
   */
  def isAllEmptyFromMultiple[A](
    items : Option[A]*) : Boolean = {
    if (isItemsEmpty(Some(items))) {
      return true
    }

    items.forall(item => isEmpty(item))
  }

  def isOptionsDefined(
    items : Option[Any]*) : Boolean = {
    val hasItems = items != null &&
      items.nonEmpty &&
      items.forall(item => isDefinedAny(item))

    hasItems
  }

  def isDefinedAny(
    item : Any,
    message : Option[String] = Some(AppConstants.NoContent)) : Boolean = {
    val hasItem = item != null &&
      String.valueOf(item) != "None" &&
      !isEmptyAny(item, None)

    //noinspection DuplicatedCode
    val hasMessage = isOptionStringDefinedWithoutMessage(message)

    if (!hasItem && hasMessage) {
      AppLogger.debug(message.get)
    }

    hasItem
  }

  def hasAnyItemDirect[A](
    items : Iterable[A],
    message : Option[String] = None) : Boolean = {
    hasAnyItem(Some(items), message)
  }

  def isItemsEmptyDirect[A](
    items : Iterable[A],
    message : Option[String] = None) : Boolean = {
    !hasAnyItem(Some(items), message)
  }

  def isDefinedDoublePlusPositive[A](
    item : Option[Double]) : Boolean = {
    item.isDefined && item.get > 0
  }

  def isDefinedIntPlusPositive[A](
    item : Option[Int]) : Boolean = {
    item.isDefined && item.get > 0
  }

  def isDefinedLongPlusPositive[A](
    item : Option[Long]) : Boolean = {
    item.isDefined && item.get > 0
  }

  def hasAnyItem[A](
    items : Option[Iterable[A]],
    message : Option[String] = None) : Boolean = {
    val hasItem = items != null &&
      items.isDefined &&
      items.get != Nil &&
      items.get != null &&
      items.get.nonEmpty

    val hasMessage = isOptionStringDefinedWithoutMessage(message)

    if (!hasItem && hasMessage) {
      AppLogger.debug(message.get)
    }

    hasItem
  }

  /**
   * Returns true of the has at least a valid character expect empty(" ")
   *
   * @param givenString
   * @param message
   *
   * @return
   */
  def isOptionStringDefinedWithoutMessage(
    givenString : Option[String]) : Boolean = {
    val hasItem = givenString != null &&
      givenString.isDefined &&
      givenString.nonEmpty &&
      !givenString.get.isBlank;

    hasItem
  }

  def isItemsDefined[A](
    items : Option[Iterable[A]],
    message : Option[String] = None) : Boolean = {
    hasAnyItem(items, message)
  }
}
