package controllers.rtb

import scala.reflect.ClassTag

class CommonJsonParsingMechanismImplementation extends CommonJsonParsingMechanism{
  override def setObjectAsJson[T](key : String, value : Option[T])(implicit classTag : ClassTag[T]) : Unit = ???

  override def getObjectFromJsonAs[T](key : String)(implicit classTag : ClassTag[T]) : Option[T] = ???

  override def setIterableObjectsAsJson[T](key : String, value : Iterable[T])(implicit classTag : ClassTag[T]) : Unit = ???

  override def getIterableObjectsAs[T](key : String)(implicit classTag : ClassTag[T]) : Iterable[T] = ???
}
