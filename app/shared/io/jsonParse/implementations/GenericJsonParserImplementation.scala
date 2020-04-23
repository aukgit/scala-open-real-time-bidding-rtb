package shared.io.jsonParse.implementations

import com.fasterxml.jackson.databind.JsonNode
import io.circe.Json
import io.circe.parser.decode
import shared.io.helpers.{ CastingHelper, EmptyValidateHelper }
import shared.io.jsonParse.traits.{ BasicJsonEncoder, GenericJsonParser }
import shared.io.loggers.AppLogger

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class GenericJsonParserImplementation[T](basicJsonEncoder : BasicJsonEncoder[T])
  extends GenericJsonParser[T] {
  override def toModel(jsonString : Option[String]) : Option[T] = {
    if (EmptyValidateHelper.isEmptyOptionString(jsonString)) {
      return None
    }

    val json = jsonString.get

    try {
      val modelEither = decode[T](json)(basicJsonEncoder.getDecoder)

      if (EmptyValidateHelper.isRightEmptyOnEither(modelEither)) {
        val model = CastingHelper.safeCastAs[T](modelEither.getOrElse(null))

        return model
      }
    }
    catch {
      case e : Exception => AppLogger.error(e)
    }

    None
  }

  override def toJsonNodes(jsonString : Option[String]) : Option[ArrayBuffer[JsonNode]] = {
    try {
      val jsonNodeOption = toJsonNode(jsonString)
      if (EmptyValidateHelper.isDefined(jsonNodeOption)) {
        val jsonNode = jsonNodeOption.get
        if (jsonNode.isArray) {
          val results = new ArrayBuffer[JsonNode]

          jsonNode.elements().forEachRemaining(w => {
            results += w
          })

          return Some(results)
        }

      }
    } catch {
      case e : Exception =>
        AppLogger.error(e)
        AppLogger.logNonFutureNullable("Json parsing failed for ", jsonString)
    }

    None
  }

  override def toModels(jsonString : Option[String]) : Option[ArrayBuffer[T]] = {
    try {
      val decoder = basicJsonEncoder.getDecoder
      val jsonNodes = toJsonNodes(jsonString)

      if (EmptyValidateHelper.isEmpty(jsonNodes)) {
        return None
      }

      val results = new mutable.ArrayBuffer[T]

      jsonNodes.foreach(w => {
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
        AppLogger.logNonFutureNullable("Json parsing failed for ", jsonString)
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
        AppLogger.logNonFutureNullable("Json parsing failed for ", model)
    }

    None
  }

  override def toJsonString(model : Option[T]) : Option[String] = {
    val jsonObject = toJsonObject(model)

    if (EmptyValidateHelper.isDefined(jsonObject)) {
      return Some(jsonObject.get.noSpaces)
    }

    None
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

  override def toJsonNode(
    jsonString : Option[String]) : Option[JsonNode] = {
    try {
      if (EmptyValidateHelper.isDefined(jsonString)) {
        val node = play.libs.Json.parse(jsonString.get)
        return Some(node)
      }
    } catch {
      case e : Exception =>
        AppLogger.error(e)
        AppLogger.logNonFutureNullable("Json parsing failed for ", jsonString)
    }

    None
  }

  override def fromModelToJsonNode(model : Option[T]) : Option[JsonNode] = {
    val jsonString = toJsonString(model)
    if(EmptyValidateHelper.isEmptyOptionString(jsonString)){
      None
    }

    toJsonNode(jsonString)
  }

  override def fromModelsToJsonNodes(
    models : Option[Iterable[T]]) : Option[Iterable[JsonNode]] = {
    if(EmptyValidateHelper.isItemsEmpty(models)){
      None
    }

    try{
      val results = models.get.map(model => fromModelToJsonNode(Some(model)).get)

      Some(results)
    } catch {
      case e: Exception =>
        AppLogger.error(e)
        AppLogger.logEntitiesNonFuture(isExecute = true, models)
    }

    None
  }

  override def toModelDirect(jsonString : String) : T = {
    val toModelOption = toModel(Some(jsonString))
    toModelOption.get
  }

  override def toJsonStringDirect(model : T) : String = {
    val toJsonOption = toJsonString(Some(model))
    toJsonOption.get
  }

  override def toJsonObjectDirect(model : T) : Json = {
    val toJsonObjectOption = toJsonObject(Some(model))
    toJsonObjectOption.get
  }
}
