package controllers.v1.apis

import controllers.webapi.core.AbstractRestWebApi
import javax.inject.Inject
import play.api.mvc._
import services._
import shared.com.ortb.persistent.schema.Tables._

class AdvertiseApiController @Inject()(
  injectedService : AdvertiseService,
  components      : ControllerComponents)
  extends AbstractRestWebApi[Advertise, AdvertiseRow, Int](components) {

  override val service = injectedService
}
