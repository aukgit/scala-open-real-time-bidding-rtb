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

class VideoPlaybackMethodRepository @Inject()(appManager: AppManager)
    extends RepositoryBase[Videoplaybackmethod, VideoplaybackmethodRow, Int](appManager) {

  override def tableName: String = this.videoPlaybackMethodTableName

  override def getEntityIdFromOptionRow(entity : Option[Tables.VideoplaybackmethodRow]): Int =
    if (entity.isDefined) entity.get.videoplaybackmethodid
    else -1

  override def setEntityIdFromOptionRow(
      entityId: Option[Int],
      entity: Option[Tables.VideoplaybackmethodRow]
  ): Option[Tables.VideoplaybackmethodRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(videoplaybackmethodid = entityId.get))
  }

  override def getAddAction(entity: Tables.VideoplaybackmethodRow)
    : FixedSqlAction[VideoplaybackmethodRow, NoStream, Effect.Write] =
    table returning table.map(_.videoplaybackmethodid) into
      ((entityProjection,
        entityId) => entityProjection.copy(videoplaybackmethodid = entityId)) += entity

  override def table =
    this.videoPlaybackMethods

  override def getDeleteAction(
      entityId: Int
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(id: Int) =
    table.filter(c => c.videoplaybackmethodid === id)

  override def getAllQuery =
    for { record <- table } yield record

  /**
    * All encoders, decoders and codec for circe
    *
    * @return
    */
  override def encoders: JsonCirceDefaultEncodersImplementation[VideoplaybackmethodRow] =
    new JsonCirceDefaultEncodersImplementation[VideoplaybackmethodRow]()
}
