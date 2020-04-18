package shared.com.ortb.persistent.repositories.pattern.traits.adapters

import io.circe.generic.auto._
import io.circe.syntax._
import shared.com.ortb.adapters.BasicAdapterImplementation
import shared.com.ortb.model.wrappers.persistent.EntityWrapperWithOptions
import shared.com.ortb.persistent.repositories.pattern.RepositoryBase
import shared.io.logger.AppLogger
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.generic.auto._
import io.circe.syntax._

trait RepositoryJsonAdapterImplementation[TTable, TRow, TKey]
  extends
    BasicAdapterImplementation with
    RepositoryJsonAdapter[TTable, TRow, TKey] {
  this : RepositoryBase[TTable, TRow, TKey] =>

  def fromEntityToJson(entity : Option[TRow]) : Option[String] = {
    convertItemTo[TRow, String](
      entity, (w) => {
        try {
          return Some(entity.asJson.spaces2)
        } catch {
          case e : Exception => AppLogger.error(e)
        }

        None
      })
  }

  def fromEntitiesToJson(entities : Option[Iterable[TRow]]) : Option[String] = {
    convertItemTo[Iterable[TRow], String](
      entities, (w) => {
        try {
          return Some(entities.asJson.spaces2)
        } catch {
          case e : Exception => AppLogger.error(e)
        }

        None
      })
  }

  def fromJsonToEntity(jsonContent : Option[String]) :
  Option[EntityWrapperWithOptions[TRow, TKey]] = {
    convertItemTo[String, EntityWrapperWithOptions[TRow, TKey]]
    (jsonContent, (w) => {
      try {
        val possibleEntity = decode[TRow](jsonContent.get)
                      .getOrElse(null)

        if(possibleEntity != null){
          val entity = possibleEntity .asInstanceOf[TRow]

          return toEntityWrapperWithOptions(Some(entity))
        }
      } catch {
        case e : Exception => AppLogger.error(e, jsonContent.get)
      }

      None
    })

    //    val isEmpty = EmptyValidateHelper.isEmpty(
    //      jsonContent,
    //      Some(AppConstants.NoContent))
    //
    //    if (isEmpty) {
    //      return None
    //    }
    //
    //    None
  }

  //
  //  override def fromTo(inputJson : Option[String]) : Option[TRow] = {
  //    try {
  //      val entityEitherOr = decode[TRow](inputJson.get)
  //      val entity = entityEitherOr.getOrElse(null)
  //
  //      Some(entity.asInstanceOf[TRow])
  //    } catch {
  //      case e : Exception => AppLogger.error(e)
  //    }
  //
  //    None
  //  }
  //
  //  override def fromManyTo(
  //    itemsInput : Option[Iterable[s]]) : Option[Iterable[B]] = {
  //    try {
  //      val entityEitherOr = decode[TRow](inputJson.get)
  //      val entity = entityEitherOr.getOrElse(null)
  //
  //      Some(entity.asInstanceOf[TRow])
  //    } catch {
  //      case e : Exception => AppLogger.error(e)
  //    }
  //
  //    None
  //  }
}
