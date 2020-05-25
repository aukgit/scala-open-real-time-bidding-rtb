package controllers.v1.apis

import shared.com.ortb.webapi.core.AbstractRestWebApi
import javax.inject.Inject
import play.api.mvc._
import services._
import shared.com.ortb.persistent.schema.Tables._

class KeywordAdvertiseMappingApiController @Inject()(
  injectedService : KeywordAdvertiseMappingService,
  components      : ControllerComponents)
  extends AbstractRestWebApi[Keywordadvertisemapping, KeywordadvertisemappingRow, Int](components) {

  override val service = injectedService
}
