package shared.com.ortb.persistent.repositories

import io.circe.generic.semiauto._
import io.circe._
import io.circe.generic.auto._
import com.google.inject.Inject
import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.schema.Tables
import shared.com.ortb.persistent.schema.Tables._
import shared.com.repository.RepositoryBase
import shared.io.jsonParse.implementations.JsonCirceDefaultEncodersImplementation
import slick.dbio.Effect
import slick.jdbc.SQLiteProfile.api._
import slick.sql.FixedSqlAction

class VideoResponseProtocolRepository @Inject()(appManager: AppManager)
    extends RepositoryBase[Videoresponseprotocol, VideoresponseprotocolRow, Int](appManager) {

  override def tableName: String = this.videoResponseProtocolTableName

  override def getEntityIdFromOptionRow(entity : Option[Tables.VideoresponseprotocolRow]): Int =
    if (entity.isDefined) entity.get.videoresponseprotocolid
    else -1

  override def setEntityIdFromOptionRow(
      entityId: Option[Int],
      entity: Option[Tables.VideoresponseprotocolRow]
  ): Option[Tables.VideoresponseprotocolRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(videoresponseprotocolid = entityId.get))
  }

  override def getAddAction(entity: Tables.VideoresponseprotocolRow)
    : FixedSqlAction[VideoresponseprotocolRow, NoStream, Effect.Write] =
    table returning table.map(_.videoresponseprotocolid) into
      ((entityProjection,
        entityId) => entityProjection.copy(videoresponseprotocolid = entityId)) += entity

  override def table =
    this.videoResponseProtocols

  override def getDeleteAction(
      entityId: Int
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(id: Int) =
    table.filter(c => c.videoresponseprotocolid === id)

  override def getAllQuery =
    for { record <- table } yield record

  /**
    * All encoders, decoders and codec for circe
    *
    * @return
    */
  override def encoders: JsonCirceDefaultEncodersImplementation[VideoresponseprotocolRow] =
    new JsonCirceDefaultEncodersImplementation[VideoresponseprotocolRow]()
}
