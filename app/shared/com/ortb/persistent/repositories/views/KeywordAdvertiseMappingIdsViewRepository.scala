package shared.com.ortb.persistent.repositories.views

import io.circe.generic.semiauto._
import io.circe._
import io.circe.generic.auto._
import com.google.inject.Inject
import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.schema.Tables._
import shared.com.repository.RepositoryViewsCore
import shared.io.jsonParse.implementations.JsonCirceDefaultEncodersImplementation

class KeywordAdvertiseMappingIdsViewRepository @Inject()(appManager: AppManager)
  extends RepositoryViewsCore[Keywordadvertisemappingidsview, KeywordadvertisemappingidsviewRow](appManager) {

  override def tableName: String = this.views.keywordAdvertiseMappingIdsViewName

  override def table =
    this.views.keywordAdvertiseMappingIdsView

  override def getAllQuery =
    for { record <- table } yield record

  /**
    * All encoders, decoders and codec for circe
    *
    * @return
    */
  override def encoders: JsonCirceDefaultEncodersImplementation[KeywordadvertisemappingidsviewRow] =
    new JsonCirceDefaultEncodersImplementation[KeywordadvertisemappingidsviewRow]()
}
