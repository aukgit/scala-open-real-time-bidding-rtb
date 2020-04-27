package shared.io.redis.traits

import com.redis.api.StringApi.{ Always, SetBehaviour }
import shared.io.jsonParse.traits.CommonJsonParsingMechanism

import scala.concurrent.duration.Duration

trait RedisKeyValueParser extends CommonJsonParsingMechanism {
  def set(
    key : String,
    value : Any,
    whenSet : SetBehaviour = Always,
    expire : Duration = null) : Unit

  def appendItemToList(
    listKey : String,
    value : Any) : Unit

  def setAnyList(
    listKey : String,
    items : Iterable[Any]) : Unit

  def setList[T](
    listKey : String,
    items : Iterable[T]) : Unit

  def setStringList(
    listKey : String,
    items : Iterable[String]) : Unit

  def getStringList(listKey : String) : Option[List[Option[String]]]

  def getListLength(listKey : String) : Option[Int]

  def setObject(
    key : String,
    value : Option[Any],
    whenSet : SetBehaviour = Always,
    expire : Duration = null) : Unit

  def clearList(listKey : String) : Unit

  def removeKeys(keys : String*) : Unit

  def getObject(key : String) : Option[Any]

  def setSerializedObjectToString(
    key : String,
    value : Option[Any],
    whenSet : SetBehaviour = Always,
    expire : Duration = null) : Unit

  def getSerializedObjectAs[T](key : String) : Option[T]

  def setSerializedObjectsToString(
    key : String,
    items : Option[Iterable[Any]],
    whenSet : SetBehaviour = Always,
    expire : Duration = null) : Unit

  def getSerializedObjectsAs[T](key : String) : Option[Iterable[T]]
}


