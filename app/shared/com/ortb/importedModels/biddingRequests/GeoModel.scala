package shared.com.ortb.importedModels.biddingRequests

case class GeoModel(
  /**
   * Country code using ISO-3166-1-alpha-3.
   */
  country : Option[String],

  /**
   * Region code using ISO-3166-2; 2-letter state code if USA.
   */
  region : Option[String],
  city : Option[String],
  lat : Option[Double],
  lon : Option[Double],
  metro : Option[String],
  zip : Option[String],

  /**
   * Local time as the number +/- of minutes from UTC.
   */
  utcoffset: Option[Int])