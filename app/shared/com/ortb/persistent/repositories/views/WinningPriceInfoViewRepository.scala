package shared.com.ortb.persistent.repositories.views
import io.circe.generic.semiauto._
import io.circe._
import io.circe.generic.auto._
import com.google.inject.Inject
import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.schema.Tables._
import shared.com.repository.RepositoryViewsCore
import shared.io.jsonParse.implementations.JsonCirceDefaultEncodersImplementation

class WinningPriceInfoViewRepository @Inject()(appManager: AppManager)
  extends RepositoryViewsCore[Winningpriceinfoview, WinningpriceinfoviewRow](appManager) {

  override def tableName: String = this.views.winningPriceInfoViewName

  override def table =
    this.views.winningPriceInfoView

  override def getAllQuery =
    for { record <- table } yield record

  /**
    * All encoders, decoders and codec for circe
    *
    * @return
    */
  override def encoders: JsonCirceDefaultEncodersImplementation[WinningpriceinfoviewRow] =
    new JsonCirceDefaultEncodersImplementation[WinningpriceinfoviewRow]()
}