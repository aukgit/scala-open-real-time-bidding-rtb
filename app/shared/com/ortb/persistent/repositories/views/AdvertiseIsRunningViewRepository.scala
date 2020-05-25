package shared.com.ortb.persistent.repositories.views

import io.circe.derivation._
import io.circe.generic.semiauto._
import io.circe._
import io.circe.generic.auto._
import com.google.inject.Inject
import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.schema.Tables._
import shared.com.repository.RepositoryViewsCore
import shared.io.jsonParse.implementations.JsonCirceDefaultEncodersImplementation

class AdvertiseIsRunningViewRepository @Inject()(appManager : AppManager)
  extends RepositoryViewsCore[Advertiseisrunningview, AdvertiseisrunningviewRow](appManager) {

  override def tableName : String = this.views.advertiseIsRunningViewName

  override def getAllQuery =
    for {record <- table} yield record

  override def table =
    this.views.advertiseIsRunningView

  /**
   * All encoders, decoders and codec for circe
   *
   * @return
   */
  override def encoders : JsonCirceDefaultEncodersImplementation[AdvertiseisrunningviewRow] =
    new JsonCirceDefaultEncodersImplementation[AdvertiseisrunningviewRow]()
}
