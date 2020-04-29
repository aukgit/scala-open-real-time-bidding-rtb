package shared.com.ortb.adapters.traits

class ModelsAdaptersConcreteImplementation extends ModelsAdapters {
  lazy val siteModelAdapter: SiteModelAdapter = new SiteModelConcreteAdapterImplementation
  lazy val bannerModelAdapter: BannerModelAdapter = new BannerModelAdapterConcreteImplementation
}
