package services

import com.google.inject.Inject
import services.core.AbstractBasicPersistentService
import shared.com.ortb.persistent.repositories._
import shared.com.ortb.persistent.schema.Tables._

class ContentCategoryService @Inject()(
  injectedRepository : ContentCategoryRepository)
  extends AbstractBasicPersistentService[Contentcategory, ContentcategoryRow, String] {

  val repository = injectedRepository
}
