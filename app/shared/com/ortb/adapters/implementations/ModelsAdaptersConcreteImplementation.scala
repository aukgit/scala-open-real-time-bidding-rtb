package shared.com.ortb.adapters.implementations

import shared.com.ortb.adapters.traits._

class ModelsAdaptersConcreteImplementation extends ModelsAdapters {
  lazy val siteModelAdapter: SiteModelAdapter = new SiteModelConcreteAdapterImplementation
  lazy val bannerModelAdapter: BannerModelAdapter = new BannerModelAdapterConcreteImplementation
}
