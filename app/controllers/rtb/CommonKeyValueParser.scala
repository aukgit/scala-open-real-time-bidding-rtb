package controllers.rtb

trait CommonKeyValueParser extends CommonJsonParsingMechanism{
  def set(key: String, value: String): Unit
  def setInt(key: String, value: Option[Int]): Unit
  def setLong(key: String, value: Option[Long]): Unit
  def setDouble(key: String, value: Option[Double]): Unit

  def appendItemToList(key: String, value: String): Unit
  def setList[T](key: String, value: Iterable[T]): Unit
  def getListAs[T](key: String): Iterable[T]
  def setStringList(key: String, value: Iterable[String]): Unit
  def getStringList(key: String): Iterable[String]
  def getListLength(key: String): Option[Int]

  def setObject(key: String, value: Option[Any]): Unit
  def getObject(key: String): Option[Any]

  def setObjectToString(key: String, value: Option[Any]): Unit
  def getObjectAsString(key: String): Option[String]

  def getObjectAs[T](key: String): Option[T]
}
