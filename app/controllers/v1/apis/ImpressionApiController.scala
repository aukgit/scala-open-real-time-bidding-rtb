package controllers.v1.apis

import controllers.webapi.core.AbstractRestWebApi
import javax.inject.Inject
import play.api.mvc._
import services.core.AbstractBasicPersistentService
import services._
import shared.com.ortb.persistent.schema.Tables._

class ImpressionApiController @Inject()(
  injectedService : ImpressionService,
  components : ControllerComponents)
  extends AbstractRestWebApi[Impression, ImpressionRow, Int] (components) {

  override val service = injectedService
}
