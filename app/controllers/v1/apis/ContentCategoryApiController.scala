package controllers.v1.apis

import controllers.webapi.core.AbstractRestWebApi
import javax.inject.Inject
import play.api.mvc._
import services._
import shared.com.ortb.persistent.schema.Tables._

class ContentCategoryApiController @Inject()(
  injectedService : ContentCategoryService,
  components      : ControllerComponents)
  extends AbstractRestWebApi[Contentcategory, ContentcategoryRow, String](components) {

  override val service = injectedService
}
