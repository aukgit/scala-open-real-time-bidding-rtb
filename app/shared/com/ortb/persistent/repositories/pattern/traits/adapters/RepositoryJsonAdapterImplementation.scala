package shared.com.ortb.persistent.repositories.pattern.traits.adapters

import java.time.Instant

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.Decoder.AccumulatingResult
import io.circe.generic.JsonCodec

import shared.com.ortb.adapters.BasicAdapterImplementation
import shared.com.ortb.model.wrappers.persistent.EntityWrapperWithOptions
import shared.com.ortb.persistent.repositories.pattern.RepositoryBase
import shared.io.helpers.EmptyValidateHelper
import shared.io.loggers.AppLogger



trait RepositoryJsonAdapterImplementation[TTable, TRow, TKey]
  extends
    BasicAdapterImplementation with
    RepositoryJsonAdapter[TTable, TRow, TKey] with
    ImplicitJsonParser[TRow] {
  this : RepositoryBase[TTable, TRow, TKey] =>

  //noinspection DuplicatedCode
  def fromEntityToJson(entity : Option[TRow]) : Option[String] = convertItemTo[TRow, String](
    entity, _ => {
      try {
        return Some(entity.asJson.spaces2)
      } catch {
        case e : Exception => AppLogger.error(e)
      }

      None
    })

  //noinspection DuplicatedCode
  def fromEntitiesToJson(entities : Option[Iterable[TRow]]) : Option[String] = {
    convertItemTo[Iterable[TRow], String](
      entities, _ => {
        try {
          return Some(entities.asJson.spaces2)
        } catch {
          case e : Exception => AppLogger.error(e)
        }

        None
      })
  }

  def fromJsonToEntityWrapper(jsonContent : Option[String]) :
  Option[EntityWrapperWithOptions[TRow, TKey]] = {
    convertItemTo[String, EntityWrapperWithOptions[TRow, TKey]]
    (jsonContent, (_) => {
      try {
        val possibleEntity = decode[TRow](jsonContent.get)(defaultDecoder)
          .getOrElse(null)

        if (possibleEntity != null) {
          val entity = possibleEntity.asInstanceOf[TRow]

          return toEntityWrapperWithOptions(Some(entity))
        }
      } catch {
        case e : Exception => AppLogger.error(e, jsonContent.get)
      }

      None
    })
  }

  override def fromJsonToEntitiesWrapper(
    jsonContent : Option[String]) :
  Option[Iterable[EntityWrapperWithOptions[TRow, TKey]]] = {
    val isEmpty = EmptyValidateHelper.isEmpty(jsonContent)
    if (isEmpty) {
      return None
    }

    try {
      val possibleEntities =decode[Iterable[TRow]](jsonContent.get)(defaultIterableDecoder)
        .getOrElse(null)

      if (possibleEntities != null) {
        val entities = possibleEntities.asInstanceOf[Iterable[TRow]]

        return toEntitiesWrapperWithOptions(Some(entities))
      }
    } catch {
      case e : Exception => AppLogger.error(e, jsonContent.get)
    }

    None
  }
}
