package shared.io.redis.traits

import com.google.inject.Inject
import com.redis.api.StringApi.{ Always, SetBehaviour }
import shared.io.jsonParse.traits.CommonJsonParsingMechanism
import shared.io.redis.implementations.RedisCommonJsonParsingMechanismImplementation

import scala.concurrent.duration.Duration

trait RedisKeyValueParser extends CommonJsonParsingMechanism {
  def set(
    key : String,
    value : String,
    whenSet : SetBehaviour = Always,
    expire : Duration = null) : Unit

  def setInt(
    key : String, value : Option[Int],
    whenSet : SetBehaviour = Always,
    expire : Duration = null) : Unit

  def setLong(
    key : String, value : Option[Long],
    whenSet : SetBehaviour = Always,
    expire : Duration = null) : Unit

  def setDouble(
    key : String, value : Option[Double],
    whenSet : SetBehaviour = Always,
    expire : Duration = null) : Unit

  def appendItemToList(
    listKey : String, value : String,
    whenSet : SetBehaviour = Always,
    expire : Duration = null) : Unit

  def setList[T](
    key : String, value : Iterable[T],
    whenSet : SetBehaviour = Always,
    expire : Duration = null) : Unit

  def getListAs[T](key : String) : Iterable[T]

  def setStringList(
    key : String, value : Iterable[String],
    whenSet : SetBehaviour = Always,
    expire : Duration = null) : Unit

  def getStringList(key : String) : Iterable[String]

  def getListLength(key : String) : Option[Int]

  def setObject(
    key : String, value : Option[Any],
    whenSet : SetBehaviour = Always,
    expire : Duration = null) : Unit

  def getObject(key : String) : Option[Any]

  def setObjectToString(
    key : String, value : Option[Any],
    whenSet : SetBehaviour = Always,
    expire : Duration = null) : Unit

  def getObjectAsString(key : String) : Option[String]

  def getObjectAs[T](key : String) : Option[T]
}


