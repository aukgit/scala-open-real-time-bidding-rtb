package shared.com.repository.traits.implementions.adapters

import io.circe.Json
import io.circe.parser.decode
import io.circe.syntax._
import shared.com.ortb.adapters.BasicAdapterImplementation
import shared.com.ortb.model.wrappers.persistent.EntityWrapperWithOptions
import shared.com.repository.RepositoryBase
import shared.com.repository.traits.adapters.RepositoryJsonAdapter
import shared.io.helpers.EmptyValidateHelper
import shared.io.jsonParse.traits.CirceJsonSupport
import shared.io.loggers.AppLogger

import scala.collection.mutable

trait RepositoryJsonAdapterImplementation[TTable, TRow, TKey]
  extends BasicAdapterImplementation
    with RepositoryJsonAdapter[TTable, TRow, TKey] with CirceJsonSupport {
  this : RepositoryBase[TTable, TRow, TKey] =>

  //noinspection DuplicatedCode
  def fromEntityToJson(entity : Option[TRow]) : Option[String] =
    convertItemTo[TRow, String](
      entity, _ => {
        try {
          val encoder = this.encoders.defaultEncoder
          return Some(entity.get.asJson(encoder).spaces2)
        } catch {
          case e : Exception => AppLogger.error(e)
        }

        None
      })

  def fromEntitiesToJsonObjects(entities : Option[Iterable[TRow]]) : Option[Iterable[Json]] = {
    convertItemTo[Iterable[TRow], Iterable[Json]](
      entities,
      _ => {
        try {
          if (entities.isDefined && entities.nonEmpty) {
            val jsonString = this.encoders.toIterableJson(entities.get)
            return Some(jsonString)
          }
        } catch {
          case e : Exception => AppLogger.error(e)
        }

        None
      }
    )
  }

  def fromEntitiesToJson(entities : Option[Iterable[TRow]]) : Option[String] = {
    convertItemTo[Iterable[TRow], String](
      entities,
      _ => {
        try {
          if (entities.isDefined && entities.nonEmpty) {
            val jsonString = this.encoders.toJsonString(entities.get)
            return Some(jsonString)
          }
        } catch {
          case e : Exception => AppLogger.error(e)
        }

        None
      }
    )
  }

  //noinspection DuplicatedCode
  def fromListEntitiesToJson(entities : Option[List[TRow]]) : Option[String] = {
    if (entities.isDefined && entities.nonEmpty) {
      return fromEntitiesToJson(entities)
    }

    None
  }

  def fromJsonToEntityWrapper(jsonContent : Option[String])
  : Option[EntityWrapperWithOptions[TRow, TKey]] = {
    def converter(jsonAsString : Option[String])
    : Option[EntityWrapperWithOptions[TRow, TKey]] = {
      try {
        val decoder = this.encoders.defaultDecoder
        val possibleEntity = decode[TRow](jsonAsString.get)(decoder)
          .getOrElse(null)

        if (possibleEntity != null) {
          val entity = possibleEntity.asInstanceOf[TRow]

          return toEntityWrapperWithOptions(Some(entity))
        }
      } catch {
        case e : Exception => AppLogger.error(e, jsonAsString.get)
      }

      None
    }

    convertItemTo(jsonContent, converter)
  }

  override def fromJsonToEntitiesWrapper(jsonContent : Option[String])
  : Option[Iterable[EntityWrapperWithOptions[TRow, TKey]]] = {
    val isEmpty = EmptyValidateHelper.isEmpty(jsonContent)
    if (isEmpty) {
      return None
    }

    try {
      val decoder = this.encoders.defaultListDecoder
//      val possibleEntities = decode[List[TRow]](jsonContent.get)(decoder)
//        .getOrElse(null)

      val possibleEntities= this.encoders.getJsonGenericParser.toModels(jsonContent)

      if (!EmptyValidateHelper.isItemsEmpty(possibleEntities)) {
        return toEntitiesWrapperWithOptions(possibleEntities)
      }
    } catch {
      case e : Exception => AppLogger.error(e, jsonContent.get)
    }

    None
  }
}
