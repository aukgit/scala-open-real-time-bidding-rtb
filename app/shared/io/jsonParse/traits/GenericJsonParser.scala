package shared.io.jsonParse.traits

import com.fasterxml.jackson.databind.JsonNode
import io.circe.Json

import scala.collection.mutable.ArrayBuffer

trait GenericJsonParser[T] {
  def toJsonNodes(jsonString : Option[String]) : Option[ArrayBuffer[JsonNode]]

  def toModel(jsonString : Option[String]) : Option[T]

  def toModelDirect(jsonString : String) : T

  def toJsonNode(jsonString : Option[String]) : Option[JsonNode]

  def fromModelToJsonNode(model : Option[T]) : Option[JsonNode]

  def fromModelsToJsonNodes(models : Option[Iterable[T]]) : Option[Iterable[JsonNode]]

  def toModels(jsonString : Option[String]) : Option[ArrayBuffer[T]]

  def toJsonObject(model : Option[T]) : Option[Json]

  def toJsonString(model : Option[T]) : Option[String]

  def toJsonObjectDirect(model : T) : Json

  def toJsonStringDirect(model : T) : String

  def fromModelsToJsonObjects(model : Option[Iterable[T]]) : Option[Iterable[Json]]
}
