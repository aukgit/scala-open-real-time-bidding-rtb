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

  def fromModelsToJsonString(models : Option[Iterable[T]]) : Option[String]

  def fromJsonStringToModels(jsonString : Option[String]) : Option[Iterable[T]]

  def toModels(jsonString : Option[String]) : Option[ArrayBuffer[T]]

  def toJsonObject(model : Option[T]) : Option[Json]

  def toJsonObjects(models : Option[Iterable[T]]) : Option[Iterable[Json]]

  /**
   * Usages 2 space json String format.
   * @param models
   * @return
   */
  def toJsonStringPrettyFormatForModels(models : Option[Iterable[T]]) : Option[String]

  /**
   * Usages 2 space json String format.
   * @param models
   * @return
   */
  def toJsonStringPrettyFormat(models : Option[T]) : Option[String]

  /**
   * Usages 2 space json String format.
   * @param models
   * @return : empty string if something went wrong or cannot parse.
   */
  def toJsonStringPrettyFormatForModelsDirect(models : Iterable[T]) : String

  /**
   * Usages 2 space json String format.
   * @param models
   * @return : empty string if something went wrong or cannot parse.
   */
  def toJsonStringPrettyFormatDirect(models : T) : String

  def toJsonString(model : Option[T]) : Option[String]

  def toJsonObjectDirect(model : T) : Json

  def toJsonStringDirect(model : T) : String

  def fromModelsToJsonObjects(model : Option[Iterable[T]]) : Option[Iterable[Json]]

  /**
   *
   * @param model
   * @param isPrettyFormat : if true then returns string using space2
   * @return
   */
  def fromJsonToJsonString(model : Option[Json], isPrettyFormat: Boolean) : Option[String]

  /**
   *
   * @param models
   * @param additionalAnnotationForItems
   * @param isPrettyFormat if true then 2 space json format.
   * @return
   */
  def fromJsonsToJsonString(
    models : Option[Iterable[Json]],
    additionalAnnotationForItems : String = null,
    isPrettyFormat:Boolean = false) : Option[String]

  def toJsonString(
    entities : Iterable[T],
    additionalAnnotationForItems : String = null) : String

  def toLogStringForEntities(
    entities : Option[Iterable[T]]) : String

  /**
   * Includes entity type name
   * @param entity : Prints entity type name.
   * @return
   */
  def toLogStringForEntity(
    entity : Option[T]) : String
}
