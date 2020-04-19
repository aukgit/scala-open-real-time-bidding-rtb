package shared.com.ortb.persistent.repositories

import com.google.inject.Inject
import slick.jdbc.SQLiteProfile.api._
import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.schema.Tables
import shared.com.ortb.persistent.schema.Tables._
import shared.com.repository.RepositoryBase
import slick.dbio.Effect
import slick.sql.FixedSqlAction

class DemandSidePlatformRepository @Inject()(appManager: AppManager)
    extends RepositoryBase[Demandsideplatform, DemandsideplatformRow, Int](
      appManager
    ) {

  override def tableName: String = this.demandSidePlatformTableName

  override def getEntityId(entity: Option[Tables.DemandsideplatformRow]): Int =
    if (entity.isDefined) entity.get.demandsideplatformid
    else -1

  override def setEntityId(
    entityId: Option[Int],
    entity: Option[Tables.DemandsideplatformRow]
  ): Option[Tables.DemandsideplatformRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(demandsideplatformid = entityId.get))
  }

  override def getAddAction(entity: Tables.DemandsideplatformRow) =
    table returning table.map(_.demandsideplatformid) into
      ((entityProjection,
        entityId) =>
         entityProjection.copy(demandsideplatformid = entityId)) += entity

  override def table = this.demandSidePlatforms

  override def getDeleteAction(
    entityId: Int
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(id: Int) =
    table.filter(c => c.demandsideplatformid === id)

  override def getAllQuery = for { record <- table } yield record
}
