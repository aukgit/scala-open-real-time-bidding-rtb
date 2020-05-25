package shared.io.jsonParse.implementations

import com.fasterxml.jackson.databind.JsonNode
import io.circe.Json
import io.circe.parser.decode
import shared.com.ortb.constants.AppConstants
import shared.io.helpers.ReflectionHelper.getTypeName
import shared.io.helpers._
import shared.io.jsonParse.traits.{ BasicJsonEncoder, GenericJsonParser }
import shared.io.loggers.AppLogger

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class GenericJsonParserImplementation[T](basicJsonEncoder : BasicJsonEncoder[T])
  extends GenericJsonParser[T] {
  lazy val quote : String = AppConstants.Quote

  override def fromJsonStringToModels(jsonString : Option[String]) : Option[Iterable[T]] = {
    val models = toModels(jsonString)
    models
  }

  override def toModels(jsonString : Option[String]) : Option[ArrayBuffer[T]] = {
    try {
      val decoder = basicJsonEncoder.getDecoder
      val jsonNodes = toJsonNodes(jsonString)

      if (EmptyValidateHelper.isEmpty(jsonNodes)) {
        return None
      }

      val results = new mutable.ArrayBuffer[T]

      jsonNodes.get.foreach(w => {
        val modelAsAny = decode[T](w.toString)(decoder).getOrElse(null)
        val model = if (modelAsAny != null) Some(modelAsAny.asInstanceOf[T]) else None
        if (EmptyValidateHelper.isDefined(model)) {
          results += model.get
        }
      })

      if (results.nonEmpty) {
        return Some(results)
      }
    } catch {
      case e : Exception =>
        AppLogger.error(e)
        AppLogger.logMaybeItem("Json parsing failed for ", jsonString)
    }

    None
  }

  override def toJsonNodes(jsonString : Option[String]) : Option[ArrayBuffer[JsonNode]] = {
    try {
      val jsonNodeOption = toJsonNode(jsonString)
      val isInvalidData = EmptyValidateHelper.isEmpty(jsonNodeOption) ||
        !jsonNodeOption.get.isArray

      if (isInvalidData) {
        return None
      }

      val jsonNode = jsonNodeOption.get
      val results = new ArrayBuffer[JsonNode]

      jsonNode.elements().forEachRemaining(currentJsonNode => {
        results += currentJsonNode
      })

      return Some(results)
    } catch {
      case e : Exception =>
        AppLogger.error(e)
        AppLogger.logMaybeItem("Json parsing failed for ", jsonString)
    }

    None
  }

  override def toJsonNode(
    jsonString : Option[String]) : Option[JsonNode] =
    JsonHelper.toJsonNode(jsonString)

  override def fromModelsToJsonNodes(
    models : Option[Iterable[T]]) : Option[Iterable[JsonNode]] = {
    if (EmptyValidateHelper.isItemsEmpty(models)) {
      None
    }

    try {
      val results = models.get.map(model => fromModelToJsonNode(Some(model)).get)

      return Some(results)
    } catch {
      case e : Exception =>
        AppLogger.error(e)
        AppLogger.logEntitiesWithCondition(isExecute = true, models)
    }

    None
  }

  override def fromModelToJsonNode(model : Option[T]) : Option[JsonNode] = {
    val jsonString = toJsonString(model)
    if (EmptyValidateHelper.isEmptyOptionString(jsonString)) {
      None
    }

    toJsonNode(jsonString)
  }

  override def toModelDirect(jsonString : String) : T = {
    val toModelOption = toModel(Some(jsonString))
    toModelOption.get
  }

  override def toModel(jsonString : Option[String]) : Option[T] = {
    if (EmptyValidateHelper.isEmptyOptionString(jsonString)) {
      return None
    }

    val json = jsonString.get

    try {
      val modelEither = decode[T](json)(basicJsonEncoder.getDecoder)

      if (EmptyValidateHelper.isRightDefinedOnEither(modelEither)) {
        val model = CastingHelper.safeCastAs[T](modelEither.getOrElse(null))

        return model
      }
    }
    catch {
      case e : Exception => AppLogger.error(e)
    }

    None
  }

  override def toJsonStringDirect(model : T) : String = {
    val toJsonOption = toJsonString(Some(model))
    toJsonOption.get
  }

  override def toJsonString(model : Option[T]) : Option[String] = {
    val jsonObject = toJsonObject(model)

    if (EmptyValidateHelper.isDefined(jsonObject)) {
      return Some(jsonObject.get.noSpaces)
    }

    None
  }

  override def toJsonObjectDirect(model : T) : Json = {
    val toJsonObjectOption = toJsonObject(Some(model))
    toJsonObjectOption.get
  }

  /**
   *
   * @param model
   * @param isPrettyFormat : if true then returns string using space2
   *
   * @return
   */
  override def fromJsonToJsonString(model : Option[Json], isPrettyFormat : Boolean) : Option[String] = {
    EmptyValidateHelper.throwOnNullOrNoneOrNil(model, Some("model is empty"))

    if (isPrettyFormat) {
      return Some(model.get.spaces2)
    }

    Some(model.get.noSpaces)
  }

  override def fromModelsToJsonString(models : Option[Iterable[T]]) : Option[String] = {
    if (EmptyValidateHelper.isItemsEmpty(models)) {
      return None
    }

    val toJsonNodes = fromModelsToJsonObjects(models)
    fromJsonsToJsonString(toJsonNodes)
  }

  override def fromModelsToJsonObjects(
    models : Option[Iterable[T]]) : Option[Iterable[Json]] = {
    if (EmptyValidateHelper.isItemsEmpty(models)) {
      return None
    }

    try {
      val results = models.get.map(model => toJsonObject(Some(model)).get)

      return Some(results)
    }
    catch {
      case e : Exception => AppLogger.error(e)
    }

    None
  }

  override def toJsonObject(model : Option[T]) : Option[Json] = {
    try {
      val encoder = basicJsonEncoder.getEncoder
      if (EmptyValidateHelper.isDefined(model)) {
        val json = encoder(model.get)
        return Some(json)
      }
    } catch {
      case e : Exception =>
        AppLogger.error(e)
        AppLogger.logMaybeItem("Json parsing failed for ", model)
    }

    None
  }

  def fromJsonsToJsonString(
    models : Option[Iterable[Json]],
    additionalAnnotationForItems : String = null,
    isPrettyFormat : Boolean = false) : Option[String] = {
    try {
      var jsonStringCollection : Iterable[String] = null
      if (isPrettyFormat) {
        jsonStringCollection = models.get.map(w => w.spaces2)
      } else {
        jsonStringCollection = models.get.map(w => w.noSpaces)
      }

      if (EmptyValidateHelper.isEmptyString(additionalAnnotationForItems)) {
        return Some(jsonStringCollection.mkString("[", ",\n", "]"))
      }

      val finalJsonString = jsonStringCollection.mkString(
        s"{${ quote }${ additionalAnnotationForItems }${ quote }:[",
        ",\n",
        "]}")

      return Some(finalJsonString)
    } catch {
      case e : Exception => AppLogger.errorCaptureAndThrow(e)
    }

    Some("")
  }

  def toJsonString(
    entities : Iterable[T],
    additionalAnnotationForItems : String = null) : String = {
    try {
      val toIterableJsonCollection = fromModelsToJsonObjects(Some(entities))

      fromJsonsToJsonString(toIterableJsonCollection, additionalAnnotationForItems)
    } catch {
      case e : Exception => AppLogger.errorCaptureAndThrow(e)
    }

    ""
  }

  override def toLogStringForEntities(entities : Option[Iterable[T]]) : String = {
    if (EmptyValidateHelper.isItemsEmpty(entities)) {
      return "Given object is null for logging."
    }

    val jsonPrettyFormatString = toJsonStringPrettyFormatForModels(entities)
    val typeName = getTypeName(Some(entities.get.head))
    val objectStringMessage = s"$typeName :\n ${ jsonPrettyFormatString }"

    objectStringMessage
  }

  override def toLogStringForEntity(entity : Option[T]) : String = {
    if (entity.isEmpty) {
      return "Given object is null for logging."
    }

    val jsonObject = toJsonObject(entity)
    val typeName = getTypeName(entity)
    val objectStringMessage = s"$typeName :\n ${ jsonObject.get.spaces2 }"

    objectStringMessage
  }

  /**
   * Usages 2 space json String format.
   *
   * @param models
   *
   * @return
   */
  override def toJsonStringPrettyFormatForModels(models : Option[Iterable[T]]) : Option[String] = {
    val jsons = toJsonObjects(models)

    if (EmptyValidateHelper.isItemsEmpty(jsons)) {
      return None
    }

    fromJsonsToJsonString(jsons, isPrettyFormat = true)
  }


  /**
   * Usages 2 space json String format.
   *
   * @param models
   *
   * @return
   */
  override def toJsonStringPrettyFormat(models : Option[T]) : Option[String] = {
    val jsons = toJsonObject(models)

    if (EmptyValidateHelper.isEmpty(jsons)) {
      return None
    }

    Some(jsons.get.spaces2)
  }

  /**
   * Usages 2 space json String format.
   *
   * @param models
   *
   * @return empty string if something went wrong or cannot parse.
   */
  override def toJsonStringPrettyFormatForModelsDirect(models : Iterable[T]) : String = {
    val jsonString = toJsonStringPrettyFormatForModels(Some(models))

    if(EmptyValidateHelper.isOptionStringDefined(jsonString)){
      return jsonString.get
    }

    ""
  }


  /**
   * Usages 2 space json String format.
   *
   * @param models
   *
   * @return empty string if something went wrong or cannot parse.
   */
  override def toJsonStringPrettyFormatDirect(model : T) : String = {
    val jsonString = toJsonStringPrettyFormat(Some(model))

    if(EmptyValidateHelper.isOptionStringDefined(jsonString)){
      return jsonString.get
    }

    ""
  }

  override def toJsonObjects(models : Option[Iterable[T]]) : Option[Iterable[Json]] = {
    fromModelsToJsonObjects(models)
  }
}
