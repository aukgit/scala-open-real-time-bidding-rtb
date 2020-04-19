package shared.com.ortb.persistent.repositories.pattern.traits.adapters

import io.circe.generic.semiauto._
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
    RepositoryJsonAdapter[TTable, TRow, TKey] {
  this : RepositoryBase[TTable, TRow, TKey] =>

  //noinspection DuplicatedCode
  def fromEntityToJson(entity : Option[TRow])
    (implicit encoder : Encoder[TRow]) :
  Option[String] = convertItemTo[TRow, String](
    entity, _ => {
      try {
        return Some(entity.get.asJson(encoder).spaces2)
      } catch {
        case e : Exception => AppLogger.error(e)
      }

      None
    })

  //noinspection DuplicatedCode
  def fromEntitiesToJson(entities : Option[List[TRow]])
    (implicit encoder : Encoder[List[TRow]])
  : Option[String] = {
    convertItemTo[List[TRow], String](
      entities, _ => {
        try {
          return Some(entities.get.asJson(encoder).spaces2)
        } catch {
          case e : Exception => AppLogger.error(e)
        }

        None
      })
  }


  def fromJsonToEntityWrapper(jsonContent : Option[String])
    (implicit decoder : Decoder[TRow]) :
  Option[EntityWrapperWithOptions[TRow, TKey]] = {
    def converter(jsonAsString : Option[String]) :
    Option[EntityWrapperWithOptions[TRow, TKey]] = {
      try {
        val possibleEntity = decode[TRow](jsonAsString.get)(decoder)
          .getOrElse(null)

        if (possibleEntity != null) {
          val entity = possibleEntity.asInstanceOf[TRow]

          return toEntityWrapperWithOptions(Some(entity))
        }
      } catch {
        case e : Exception => AppLogger.error(e, jsonContent.get)
      }

      None
    }

    convertItemTo(jsonContent, converter)
  }

  override def fromJsonToEntitiesWrapper(jsonContent : Option[String])
    (implicit decoder : Decoder[List[TRow]]) :
  Option[List[EntityWrapperWithOptions[TRow, TKey]]] = {
    val isEmpty = EmptyValidateHelper.isEmpty(jsonContent)
    if (isEmpty) {
      return None
    }

    try {
      val possibleEntities = decode[List[TRow]](jsonContent.get)(decoder)
        .getOrElse(null)

      if (possibleEntities != null) {
        val entities = possibleEntities.asInstanceOf[List[TRow]]

        return toEntitiesWrapperWithOptions(Some(entities))
      }
    } catch {
      case e : Exception => AppLogger.error(e, jsonContent.get)
    }

    None
  }
}
