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

  def setStringList(
    listKey : String,
    items : Iterable[String]) : Unit

  def getStringList(listKey : String) : Option[List[Option[String]]]

  def getListLength(listKey : String) : Option[Int]

  def clearList(listKey : String) : Unit

  def removeKeys(keys : String*) : Unit

  def getObject(key : String) : Option[Any]

  def setSerializedObjectToBytes(
    key : String,
    value : Option[Any],
    whenSet : SetBehaviour = Always,
    expire : Duration = null) : Option[Array[Byte]]

  def getDeserializeObjectFromBytesAs[T](key : String) : Option[T]

  def setSerializedObjectsToBytes(
    key : String,
    items : Option[List[Any]],
    whenSet : SetBehaviour = Always,
    expire : Duration = null) : Option[Array[Byte]]

  def getSerializedObjectsAs[T](key : String) : Option[List[T]]
}


