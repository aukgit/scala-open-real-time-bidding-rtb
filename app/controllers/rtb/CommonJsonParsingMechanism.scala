package controllers.rtb

import scala.reflect.ClassTag

trait CommonJsonParsingMechanism{
  def setObjectAsJson[T](key: String, value: Option[T])(implicit classTag : ClassTag[T]): Unit
  def getObjectFromJsonAs[T](key: String)(implicit classTag : ClassTag[T]): Option[T]

  def setIterableObjectsAsJson[T](key: String, value: Iterable[T])(implicit classTag : ClassTag[T]): Unit
  def getIterableObjectsAs[T](key: String)(implicit classTag : ClassTag[T]): Iterable[T]
}
