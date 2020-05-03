package shared.com.ortb.persistent.repositories.views

import io.circe.generic.semiauto._
import io.circe._
import io.circe.generic.auto._
import com.google.inject.Inject
import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.schema.Tables
import shared.com.ortb.persistent.schema.Tables._
import shared.com.repository.{ RepositoryBase, RepositoryViewsCore }
import shared.io.jsonParse.implementations.JsonCirceDefaultEncodersImplementation
import slick.dbio.Effect
import slick.jdbc.SQLiteProfile.api._
import slick.sql.FixedSqlAction

class BidRelatedIdsViewRepository @Inject()(appManager: AppManager)
    extends RepositoryViewsCore[Bidrelatedidsview, BidrelatedidsviewRow](appManager) {

  override def tableName: String = this.bidTableName

  override def table =
    this.views.bidRelatedIdsView

  override def getAllQuery =
    for { record <- table } yield record

  /**
    * All encoders, decoders and codec for circe
    *
    * @return
    */
  override def encoders: JsonCirceDefaultEncodersImplementation[BidrelatedidsviewRow] =
    new JsonCirceDefaultEncodersImplementation[BidrelatedidsviewRow]()
}
