package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.SQLiteProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Array(Advertise.schema, Banneradvertisetype.schema, Bidrequest.schema, Bidresponse.schema, Campaign.schema, Campaigntargetcity.schema, Campaigntargetsite.schema, Contentcategory.schema, Contentcontext.schema, Demandsideplatform.schema, Geomapping.schema, Impression.schema, Nobidresponse.schema, Nobidresponsetype.schema, Transaction.schema).reduceLeft(_ ++ _)
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Advertise
   *  @param advertiseid Database column AdvertiseID SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param campaignid Database column CampaignID SqlType(INTEGER)
   *  @param banneradvertisetypeid Database column BannerAdvertiseTypeID SqlType(INTEGER)
   *  @param advertisetitle Database column AdvertiseTitle SqlType(TEXT)
   *  @param contentcontextid Database column ContentContextId SqlType(INTEGER)
   *  @param bidurl Database column BidUrl SqlType(TEXT)
   *  @param iframehtml Database column IFrameHtml SqlType(TEXT)
   *  @param iscountryspecific Database column IsCountrySpecific SqlType(INTEGER), Default(0)
   *  @param isvideo Database column IsVideo SqlType(INTEGER), Default(0)
   *  @param impressioncount Database column ImpressionCount SqlType(INTEGER), Default(0)
   *  @param height Database column Height SqlType(REAL), Default(0.0)
   *  @param weight Database column Weight SqlType(REAL), Default(0.0)
   *  @param minheight Database column MinHeight SqlType(REAL), Default(0.0)
   *  @param minweight Database column MinWeight SqlType(REAL), Default(0.0)
   *  @param maxheight Database column MaxHeight SqlType(REAL), Default(0.0)
   *  @param maxweight Database column MaxWeight SqlType(REAL), Default(0.0)
   *  @param hasagerestriction Database column HasAgeRestriction SqlType(INTEGER)
   *  @param minage Database column MinAge SqlType(INTEGER), Default(Some(0))
   *  @param maxage Database column MaxAge SqlType(INTEGER), Default(Some(0)) */
  case class AdvertiseRow(advertiseid: Int, campaignid: Int, banneradvertisetypeid: Int, advertisetitle: String, contentcontextid: Int, bidurl: String, iframehtml: Option[String], iscountryspecific: Int = 0, isvideo: Int = 0, impressioncount: Int = 0, height: Double = 0.0, weight: Double = 0.0, minheight: Double = 0.0, minweight: Double = 0.0, maxheight: Double = 0.0, maxweight: Double = 0.0, hasagerestriction: Int, minage: Option[Int] = Some(0), maxage: Option[Int] = Some(0))
  /** GetResult implicit for fetching AdvertiseRow objects using plain SQL queries */
  implicit def GetResultAdvertiseRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]], e3: GR[Double], e4: GR[Option[Int]]): GR[AdvertiseRow] = GR{
    prs => import prs._
    AdvertiseRow.tupled((<<[Int], <<[Int], <<[Int], <<[String], <<[Int], <<[String], <<?[String], <<[Int], <<[Int], <<[Int], <<[Double], <<[Double], <<[Double], <<[Double], <<[Double], <<[Double], <<[Int], <<?[Int], <<?[Int]))
  }
  /** Table description of table Advertise. Objects of this class serve as prototypes for rows in queries. */
  class Advertise(_tableTag: Tag) extends profile.api.Table[AdvertiseRow](_tableTag, "Advertise") {
    def * = (advertiseid, campaignid, banneradvertisetypeid, advertisetitle, contentcontextid, bidurl, iframehtml, iscountryspecific, isvideo, impressioncount, height, weight, minheight, minweight, maxheight, maxweight, hasagerestriction, minage, maxage) <> (AdvertiseRow.tupled, AdvertiseRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(advertiseid), Rep.Some(campaignid), Rep.Some(banneradvertisetypeid), Rep.Some(advertisetitle), Rep.Some(contentcontextid), Rep.Some(bidurl), iframehtml, Rep.Some(iscountryspecific), Rep.Some(isvideo), Rep.Some(impressioncount), Rep.Some(height), Rep.Some(weight), Rep.Some(minheight), Rep.Some(minweight), Rep.Some(maxheight), Rep.Some(maxweight), Rep.Some(hasagerestriction), minage, maxage)).shaped.<>({r=>import r._; _1.map(_=> AdvertiseRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7, _8.get, _9.get, _10.get, _11.get, _12.get, _13.get, _14.get, _15.get, _16.get, _17.get, _18, _19)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column AdvertiseID SqlType(INTEGER), AutoInc, PrimaryKey */
    val advertiseid: Rep[Int] = column[Int]("AdvertiseID", O.AutoInc, O.PrimaryKey)
    /** Database column CampaignID SqlType(INTEGER) */
    val campaignid: Rep[Int] = column[Int]("CampaignID")
    /** Database column BannerAdvertiseTypeID SqlType(INTEGER) */
    val banneradvertisetypeid: Rep[Int] = column[Int]("BannerAdvertiseTypeID")
    /** Database column AdvertiseTitle SqlType(TEXT) */
    val advertisetitle: Rep[String] = column[String]("AdvertiseTitle")
    /** Database column ContentContextId SqlType(INTEGER) */
    val contentcontextid: Rep[Int] = column[Int]("ContentContextId")
    /** Database column BidUrl SqlType(TEXT) */
    val bidurl: Rep[String] = column[String]("BidUrl")
    /** Database column IFrameHtml SqlType(TEXT) */
    val iframehtml: Rep[Option[String]] = column[Option[String]]("IFrameHtml")
    /** Database column IsCountrySpecific SqlType(INTEGER), Default(0) */
    val iscountryspecific: Rep[Int] = column[Int]("IsCountrySpecific", O.Default(0))
    /** Database column IsVideo SqlType(INTEGER), Default(0) */
    val isvideo: Rep[Int] = column[Int]("IsVideo", O.Default(0))
    /** Database column ImpressionCount SqlType(INTEGER), Default(0) */
    val impressioncount: Rep[Int] = column[Int]("ImpressionCount", O.Default(0))
    /** Database column Height SqlType(REAL), Default(0.0) */
    val height: Rep[Double] = column[Double]("Height", O.Default(0.0))
    /** Database column Weight SqlType(REAL), Default(0.0) */
    val weight: Rep[Double] = column[Double]("Weight", O.Default(0.0))
    /** Database column MinHeight SqlType(REAL), Default(0.0) */
    val minheight: Rep[Double] = column[Double]("MinHeight", O.Default(0.0))
    /** Database column MinWeight SqlType(REAL), Default(0.0) */
    val minweight: Rep[Double] = column[Double]("MinWeight", O.Default(0.0))
    /** Database column MaxHeight SqlType(REAL), Default(0.0) */
    val maxheight: Rep[Double] = column[Double]("MaxHeight", O.Default(0.0))
    /** Database column MaxWeight SqlType(REAL), Default(0.0) */
    val maxweight: Rep[Double] = column[Double]("MaxWeight", O.Default(0.0))
    /** Database column HasAgeRestriction SqlType(INTEGER) */
    val hasagerestriction: Rep[Int] = column[Int]("HasAgeRestriction")
    /** Database column MinAge SqlType(INTEGER), Default(Some(0)) */
    val minage: Rep[Option[Int]] = column[Option[Int]]("MinAge", O.Default(Some(0)))
    /** Database column MaxAge SqlType(INTEGER), Default(Some(0)) */
    val maxage: Rep[Option[Int]] = column[Option[Int]]("MaxAge", O.Default(Some(0)))

    /** Foreign key referencing Banneradvertisetype (database name BannerAdvertiseType_FK_1) */
    lazy val banneradvertisetypeFk = foreignKey("BannerAdvertiseType_FK_1", banneradvertisetypeid, Banneradvertisetype)(r => r.banneradvertisetypeid, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
    /** Foreign key referencing Campaign (database name Campaign_FK_2) */
    lazy val campaignFk = foreignKey("Campaign_FK_2", campaignid, Campaign)(r => r.campaignid, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
    /** Foreign key referencing Contentcontext (database name ContentContext_FK_3) */
    lazy val contentcontextFk = foreignKey("ContentContext_FK_3", contentcontextid, Contentcontext)(r => r.contentcontextid, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Advertise */
  lazy val Advertise = new TableQuery(tag => new Advertise(tag))

  /** Entity class storing rows of table Banneradvertisetype
   *  @param banneradvertisetypeid Database column BannerAdvertiseTypeID SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param typename Database column TypeName SqlType(TEXT) */
  case class BanneradvertisetypeRow(banneradvertisetypeid: Int, typename: String)
  /** GetResult implicit for fetching BanneradvertisetypeRow objects using plain SQL queries */
  implicit def GetResultBanneradvertisetypeRow(implicit e0: GR[Int], e1: GR[String]): GR[BanneradvertisetypeRow] = GR{
    prs => import prs._
    BanneradvertisetypeRow.tupled((<<[Int], <<[String]))
  }
  /** Table description of table BannerAdvertiseType. Objects of this class serve as prototypes for rows in queries. */
  class Banneradvertisetype(_tableTag: Tag) extends profile.api.Table[BanneradvertisetypeRow](_tableTag, "BannerAdvertiseType") {
    def * = (banneradvertisetypeid, typename) <> (BanneradvertisetypeRow.tupled, BanneradvertisetypeRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(banneradvertisetypeid), Rep.Some(typename))).shaped.<>({r=>import r._; _1.map(_=> BanneradvertisetypeRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column BannerAdvertiseTypeID SqlType(INTEGER), AutoInc, PrimaryKey */
    val banneradvertisetypeid: Rep[Int] = column[Int]("BannerAdvertiseTypeID", O.AutoInc, O.PrimaryKey)
    /** Database column TypeName SqlType(TEXT) */
    val typename: Rep[String] = column[String]("TypeName")
  }
  /** Collection-like TableQuery object for table Banneradvertisetype */
  lazy val Banneradvertisetype = new TableQuery(tag => new Banneradvertisetype(tag))

  /** Entity class storing rows of table Bidrequest
   *  @param bidrequestid Database column BidRequestId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param demandsideplatformid Database column DemandSidePlatformID SqlType(INTEGER)
   *  @param isbanner Database column IsBanner SqlType(INTEGER), Default(0)
   *  @param isvideo Database column IsVideo SqlType(INTEGER), Default(0)
   *  @param height Database column Height SqlType(INTEGER)
   *  @param width Database column Width SqlType(INTEGER)
   *  @param countries Database column Countries SqlType(TEXT)
   *  @param cities Database column Cities SqlType(TEXT)
   *  @param targetedsites Database column TargetedSites SqlType(TEXT)
   *  @param targetedcities Database column TargetedCities SqlType(TEXT)
   *  @param rawbidrequest Database column RawBidRequest SqlType(TEXT) */
  case class BidrequestRow(bidrequestid: Int, demandsideplatformid: Int, isbanner: Int = 0, isvideo: Int = 0, height: Option[Int], width: Option[Int], countries: Option[String], cities: Option[String], targetedsites: Option[String], targetedcities: Option[String], rawbidrequest: Option[String])
  /** GetResult implicit for fetching BidrequestRow objects using plain SQL queries */
  implicit def GetResultBidrequestRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[Option[String]]): GR[BidrequestRow] = GR{
    prs => import prs._
    BidrequestRow.tupled((<<[Int], <<[Int], <<[Int], <<[Int], <<?[Int], <<?[Int], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table BidRequest. Objects of this class serve as prototypes for rows in queries. */
  class Bidrequest(_tableTag: Tag) extends profile.api.Table[BidrequestRow](_tableTag, "BidRequest") {
    def * = (bidrequestid, demandsideplatformid, isbanner, isvideo, height, width, countries, cities, targetedsites, targetedcities, rawbidrequest) <> (BidrequestRow.tupled, BidrequestRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(bidrequestid), Rep.Some(demandsideplatformid), Rep.Some(isbanner), Rep.Some(isvideo), height, width, countries, cities, targetedsites, targetedcities, rawbidrequest)).shaped.<>({r=>import r._; _1.map(_=> BidrequestRow.tupled((_1.get, _2.get, _3.get, _4.get, _5, _6, _7, _8, _9, _10, _11)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column BidRequestId SqlType(INTEGER), AutoInc, PrimaryKey */
    val bidrequestid: Rep[Int] = column[Int]("BidRequestId", O.AutoInc, O.PrimaryKey)
    /** Database column DemandSidePlatformID SqlType(INTEGER) */
    val demandsideplatformid: Rep[Int] = column[Int]("DemandSidePlatformID")
    /** Database column IsBanner SqlType(INTEGER), Default(0) */
    val isbanner: Rep[Int] = column[Int]("IsBanner", O.Default(0))
    /** Database column IsVideo SqlType(INTEGER), Default(0) */
    val isvideo: Rep[Int] = column[Int]("IsVideo", O.Default(0))
    /** Database column Height SqlType(INTEGER) */
    val height: Rep[Option[Int]] = column[Option[Int]]("Height")
    /** Database column Width SqlType(INTEGER) */
    val width: Rep[Option[Int]] = column[Option[Int]]("Width")
    /** Database column Countries SqlType(TEXT) */
    val countries: Rep[Option[String]] = column[Option[String]]("Countries")
    /** Database column Cities SqlType(TEXT) */
    val cities: Rep[Option[String]] = column[Option[String]]("Cities")
    /** Database column TargetedSites SqlType(TEXT) */
    val targetedsites: Rep[Option[String]] = column[Option[String]]("TargetedSites")
    /** Database column TargetedCities SqlType(TEXT) */
    val targetedcities: Rep[Option[String]] = column[Option[String]]("TargetedCities")
    /** Database column RawBidRequest SqlType(TEXT) */
    val rawbidrequest: Rep[Option[String]] = column[Option[String]]("RawBidRequest")

    /** Foreign key referencing Demandsideplatform (database name DemandSidePlatform_FK_1) */
    lazy val demandsideplatformFk = foreignKey("DemandSidePlatform_FK_1", demandsideplatformid, Demandsideplatform)(r => r.demandsideplatformid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Bidrequest */
  lazy val Bidrequest = new TableQuery(tag => new Bidrequest(tag))

  /** Entity class storing rows of table Bidresponse
   *  @param bidresponseid Database column BidResponseID SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param price Database column Price SqlType(REAL)
   *  @param currency Database column Currency SqlType(TEXT)
   *  @param adm Database column Adm SqlType(TEXT)
   *  @param nurl Database column NUrl SqlType(TEXT)
   *  @param iurl Database column IUrl SqlType(TEXT)
   *  @param advertiseid Database column AdvertiseID SqlType(INTEGER)
   *  @param bidrequestid Database column BidRequestID SqlType(INTEGER) */
  case class BidresponseRow(bidresponseid: Int, price: Double, currency: String, adm: Option[String], nurl: String, iurl: Option[String], advertiseid: Int, bidrequestid: Option[Int])
  /** GetResult implicit for fetching BidresponseRow objects using plain SQL queries */
  implicit def GetResultBidresponseRow(implicit e0: GR[Int], e1: GR[Double], e2: GR[String], e3: GR[Option[String]], e4: GR[Option[Int]]): GR[BidresponseRow] = GR{
    prs => import prs._
    BidresponseRow.tupled((<<[Int], <<[Double], <<[String], <<?[String], <<[String], <<?[String], <<[Int], <<?[Int]))
  }
  /** Table description of table BidResponse. Objects of this class serve as prototypes for rows in queries. */
  class Bidresponse(_tableTag: Tag) extends profile.api.Table[BidresponseRow](_tableTag, "BidResponse") {
    def * = (bidresponseid, price, currency, adm, nurl, iurl, advertiseid, bidrequestid) <> (BidresponseRow.tupled, BidresponseRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(bidresponseid), Rep.Some(price), Rep.Some(currency), adm, Rep.Some(nurl), iurl, Rep.Some(advertiseid), bidrequestid)).shaped.<>({r=>import r._; _1.map(_=> BidresponseRow.tupled((_1.get, _2.get, _3.get, _4, _5.get, _6, _7.get, _8)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column BidResponseID SqlType(INTEGER), AutoInc, PrimaryKey */
    val bidresponseid: Rep[Int] = column[Int]("BidResponseID", O.AutoInc, O.PrimaryKey)
    /** Database column Price SqlType(REAL) */
    val price: Rep[Double] = column[Double]("Price")
    /** Database column Currency SqlType(TEXT) */
    val currency: Rep[String] = column[String]("Currency")
    /** Database column Adm SqlType(TEXT) */
    val adm: Rep[Option[String]] = column[Option[String]]("Adm")
    /** Database column NUrl SqlType(TEXT) */
    val nurl: Rep[String] = column[String]("NUrl")
    /** Database column IUrl SqlType(TEXT) */
    val iurl: Rep[Option[String]] = column[Option[String]]("IUrl")
    /** Database column AdvertiseID SqlType(INTEGER) */
    val advertiseid: Rep[Int] = column[Int]("AdvertiseID")
    /** Database column BidRequestID SqlType(INTEGER) */
    val bidrequestid: Rep[Option[Int]] = column[Option[Int]]("BidRequestID")

    /** Foreign key referencing Advertise (database name Advertise_FK_1) */
    lazy val advertiseFk = foreignKey("Advertise_FK_1", advertiseid, Advertise)(r => r.advertiseid, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
    /** Foreign key referencing Bidrequest (database name BidRequest_FK_2) */
    lazy val bidrequestFk = foreignKey("BidRequest_FK_2", bidrequestid, Bidrequest)(r => Rep.Some(r.bidrequestid), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Bidresponse */
  lazy val Bidresponse = new TableQuery(tag => new Bidresponse(tag))

  /** Entity class storing rows of table Campaign
   *  @param campaignid Database column CampaignID SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param campaignname Database column CampaignName SqlType(TEXT)
   *  @param contentcategoryid Database column ContentCategoryID SqlType(TEXT)
   *  @param totalbudgetcpm Database column TotalBudgetCPM SqlType(REAL), Default(0.0)
   *  @param spendalready Database column SpendAlready SqlType(REAL), Default(0.0)
   *  @param remainingamount Database column RemainingAmount SqlType(REAL), Default(0.0)
   *  @param startdate Database column StartDate SqlType(REAL), Default(Some(0.0))
   *  @param enddate Database column EndDate SqlType(REAL), Default(Some(0.0))
   *  @param impressioncount Database column ImpressionCount SqlType(INTEGER), Default(0)
   *  @param demandsideplatformid Database column DemandSidePlatformId SqlType(INTEGER)
   *  @param isrunning Database column IsRunning SqlType(INTEGER), Default(0)
   *  @param priority Database column Priority SqlType(INTEGER), Default(999)
   *  @param isretricttousergender Database column IsRetrictToUserGender SqlType(INTEGER), Default(0)
   *  @param expectedusergender Database column ExpectedUserGender SqlType(TEXT), Length(2,false) */
  case class CampaignRow(campaignid: Int, campaignname: String, contentcategoryid: String, totalbudgetcpm: Double = 0.0, spendalready: Double = 0.0, remainingamount: Double = 0.0, startdate: Option[Double] = Some(0.0), enddate: Option[Double] = Some(0.0), impressioncount: Int = 0, demandsideplatformid: Int, isrunning: Int = 0, priority: Int = 999, isretricttousergender: Int = 0, expectedusergender: Option[String])
  /** GetResult implicit for fetching CampaignRow objects using plain SQL queries */
  implicit def GetResultCampaignRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Double], e3: GR[Option[Double]], e4: GR[Option[String]]): GR[CampaignRow] = GR{
    prs => import prs._
    CampaignRow.tupled((<<[Int], <<[String], <<[String], <<[Double], <<[Double], <<[Double], <<?[Double], <<?[Double], <<[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<?[String]))
  }
  /** Table description of table Campaign. Objects of this class serve as prototypes for rows in queries. */
  class Campaign(_tableTag: Tag) extends profile.api.Table[CampaignRow](_tableTag, "Campaign") {
    def * = (campaignid, campaignname, contentcategoryid, totalbudgetcpm, spendalready, remainingamount, startdate, enddate, impressioncount, demandsideplatformid, isrunning, priority, isretricttousergender, expectedusergender) <> (CampaignRow.tupled, CampaignRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(campaignid), Rep.Some(campaignname), Rep.Some(contentcategoryid), Rep.Some(totalbudgetcpm), Rep.Some(spendalready), Rep.Some(remainingamount), startdate, enddate, Rep.Some(impressioncount), Rep.Some(demandsideplatformid), Rep.Some(isrunning), Rep.Some(priority), Rep.Some(isretricttousergender), expectedusergender)).shaped.<>({r=>import r._; _1.map(_=> CampaignRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7, _8, _9.get, _10.get, _11.get, _12.get, _13.get, _14)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column CampaignID SqlType(INTEGER), AutoInc, PrimaryKey */
    val campaignid: Rep[Int] = column[Int]("CampaignID", O.AutoInc, O.PrimaryKey)
    /** Database column CampaignName SqlType(TEXT) */
    val campaignname: Rep[String] = column[String]("CampaignName")
    /** Database column ContentCategoryID SqlType(TEXT) */
    val contentcategoryid: Rep[String] = column[String]("ContentCategoryID")
    /** Database column TotalBudgetCPM SqlType(REAL), Default(0.0) */
    val totalbudgetcpm: Rep[Double] = column[Double]("TotalBudgetCPM", O.Default(0.0))
    /** Database column SpendAlready SqlType(REAL), Default(0.0) */
    val spendalready: Rep[Double] = column[Double]("SpendAlready", O.Default(0.0))
    /** Database column RemainingAmount SqlType(REAL), Default(0.0) */
    val remainingamount: Rep[Double] = column[Double]("RemainingAmount", O.Default(0.0))
    /** Database column StartDate SqlType(REAL), Default(Some(0.0)) */
    val startdate: Rep[Option[Double]] = column[Option[Double]]("StartDate", O.Default(Some(0.0)))
    /** Database column EndDate SqlType(REAL), Default(Some(0.0)) */
    val enddate: Rep[Option[Double]] = column[Option[Double]]("EndDate", O.Default(Some(0.0)))
    /** Database column ImpressionCount SqlType(INTEGER), Default(0) */
    val impressioncount: Rep[Int] = column[Int]("ImpressionCount", O.Default(0))
    /** Database column DemandSidePlatformId SqlType(INTEGER) */
    val demandsideplatformid: Rep[Int] = column[Int]("DemandSidePlatformId")
    /** Database column IsRunning SqlType(INTEGER), Default(0) */
    val isrunning: Rep[Int] = column[Int]("IsRunning", O.Default(0))
    /** Database column Priority SqlType(INTEGER), Default(999) */
    val priority: Rep[Int] = column[Int]("Priority", O.Default(999))
    /** Database column IsRetrictToUserGender SqlType(INTEGER), Default(0) */
    val isretricttousergender: Rep[Int] = column[Int]("IsRetrictToUserGender", O.Default(0))
    /** Database column ExpectedUserGender SqlType(TEXT), Length(2,false) */
    val expectedusergender: Rep[Option[String]] = column[Option[String]]("ExpectedUserGender", O.Length(2,varying=false))

    /** Foreign key referencing Contentcategory (database name ContentCategory_FK_1) */
    lazy val contentcategoryFk = foreignKey("ContentCategory_FK_1", contentcategoryid, Contentcategory)(r => r.contentcategoryid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Demandsideplatform (database name DemandSidePlatform_FK_2) */
    lazy val demandsideplatformFk = foreignKey("DemandSidePlatform_FK_2", demandsideplatformid, Demandsideplatform)(r => r.demandsideplatformid, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Campaign */
  lazy val Campaign = new TableQuery(tag => new Campaign(tag))

  /** Entity class storing rows of table Campaigntargetcity
   *  @param campaigntargetcityid Database column CampaignTargetCityID SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param campaigntargetcity Database column CampaignTargetCity SqlType(TEXT)
   *  @param campaignid Database column CampaignID SqlType(INTEGER) */
  case class CampaigntargetcityRow(campaigntargetcityid: Int, campaigntargetcity: String, campaignid: Int)
  /** GetResult implicit for fetching CampaigntargetcityRow objects using plain SQL queries */
  implicit def GetResultCampaigntargetcityRow(implicit e0: GR[Int], e1: GR[String]): GR[CampaigntargetcityRow] = GR{
    prs => import prs._
    CampaigntargetcityRow.tupled((<<[Int], <<[String], <<[Int]))
  }
  /** Table description of table CampaignTargetCity. Objects of this class serve as prototypes for rows in queries. */
  class Campaigntargetcity(_tableTag: Tag) extends profile.api.Table[CampaigntargetcityRow](_tableTag, "CampaignTargetCity") {
    def * = (campaigntargetcityid, campaigntargetcity, campaignid) <> (CampaigntargetcityRow.tupled, CampaigntargetcityRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(campaigntargetcityid), Rep.Some(campaigntargetcity), Rep.Some(campaignid))).shaped.<>({r=>import r._; _1.map(_=> CampaigntargetcityRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column CampaignTargetCityID SqlType(INTEGER), AutoInc, PrimaryKey */
    val campaigntargetcityid: Rep[Int] = column[Int]("CampaignTargetCityID", O.AutoInc, O.PrimaryKey)
    /** Database column CampaignTargetCity SqlType(TEXT) */
    val campaigntargetcity: Rep[String] = column[String]("CampaignTargetCity")
    /** Database column CampaignID SqlType(INTEGER) */
    val campaignid: Rep[Int] = column[Int]("CampaignID")

    /** Foreign key referencing Campaign (database name Campaign_FK_1) */
    lazy val campaignFk = foreignKey("Campaign_FK_1", campaignid, Campaign)(r => r.campaignid, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Campaigntargetcity */
  lazy val Campaigntargetcity = new TableQuery(tag => new Campaigntargetcity(tag))

  /** Entity class storing rows of table Campaigntargetsite
   *  @param campaigntargetsiteid Database column CampaignTargetSiteID SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param campaigntargetsite Database column CampaignTargetSite SqlType(TEXT)
   *  @param campaignid Database column CampaignID SqlType(INTEGER) */
  case class CampaigntargetsiteRow(campaigntargetsiteid: Int, campaigntargetsite: String, campaignid: Int)
  /** GetResult implicit for fetching CampaigntargetsiteRow objects using plain SQL queries */
  implicit def GetResultCampaigntargetsiteRow(implicit e0: GR[Int], e1: GR[String]): GR[CampaigntargetsiteRow] = GR{
    prs => import prs._
    CampaigntargetsiteRow.tupled((<<[Int], <<[String], <<[Int]))
  }
  /** Table description of table CampaignTargetSite. Objects of this class serve as prototypes for rows in queries. */
  class Campaigntargetsite(_tableTag: Tag) extends profile.api.Table[CampaigntargetsiteRow](_tableTag, "CampaignTargetSite") {
    def * = (campaigntargetsiteid, campaigntargetsite, campaignid) <> (CampaigntargetsiteRow.tupled, CampaigntargetsiteRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(campaigntargetsiteid), Rep.Some(campaigntargetsite), Rep.Some(campaignid))).shaped.<>({r=>import r._; _1.map(_=> CampaigntargetsiteRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column CampaignTargetSiteID SqlType(INTEGER), AutoInc, PrimaryKey */
    val campaigntargetsiteid: Rep[Int] = column[Int]("CampaignTargetSiteID", O.AutoInc, O.PrimaryKey)
    /** Database column CampaignTargetSite SqlType(TEXT) */
    val campaigntargetsite: Rep[String] = column[String]("CampaignTargetSite")
    /** Database column CampaignID SqlType(INTEGER) */
    val campaignid: Rep[Int] = column[Int]("CampaignID")

    /** Foreign key referencing Campaign (database name Campaign_FK_1) */
    lazy val campaignFk = foreignKey("Campaign_FK_1", campaignid, Campaign)(r => r.campaignid, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Campaigntargetsite */
  lazy val Campaigntargetsite = new TableQuery(tag => new Campaigntargetsite(tag))

  /** Entity class storing rows of table Contentcategory
   *  @param contentcategoryid Database column ContentCategoryID SqlType(TEXT), PrimaryKey
   *  @param contentcategoryname Database column ContentCategoryName SqlType(TEXT) */
  case class ContentcategoryRow(contentcategoryid: String, contentcategoryname: String)
  /** GetResult implicit for fetching ContentcategoryRow objects using plain SQL queries */
  implicit def GetResultContentcategoryRow(implicit e0: GR[String]): GR[ContentcategoryRow] = GR{
    prs => import prs._
    ContentcategoryRow.tupled((<<[String], <<[String]))
  }
  /** Table description of table ContentCategory. Objects of this class serve as prototypes for rows in queries. */
  class Contentcategory(_tableTag: Tag) extends profile.api.Table[ContentcategoryRow](_tableTag, "ContentCategory") {
    def * = (contentcategoryid, contentcategoryname) <> (ContentcategoryRow.tupled, ContentcategoryRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(contentcategoryid), Rep.Some(contentcategoryname))).shaped.<>({r=>import r._; _1.map(_=> ContentcategoryRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ContentCategoryID SqlType(TEXT), PrimaryKey */
    val contentcategoryid: Rep[String] = column[String]("ContentCategoryID", O.PrimaryKey)
    /** Database column ContentCategoryName SqlType(TEXT) */
    val contentcategoryname: Rep[String] = column[String]("ContentCategoryName")
  }
  /** Collection-like TableQuery object for table Contentcategory */
  lazy val Contentcategory = new TableQuery(tag => new Contentcategory(tag))

  /** Entity class storing rows of table Contentcontext
   *  @param contentcontextid Database column ContentContextId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param context Database column Context SqlType(TEXT) */
  case class ContentcontextRow(contentcontextid: Int, context: String)
  /** GetResult implicit for fetching ContentcontextRow objects using plain SQL queries */
  implicit def GetResultContentcontextRow(implicit e0: GR[Int], e1: GR[String]): GR[ContentcontextRow] = GR{
    prs => import prs._
    ContentcontextRow.tupled((<<[Int], <<[String]))
  }
  /** Table description of table ContentContext. Objects of this class serve as prototypes for rows in queries. */
  class Contentcontext(_tableTag: Tag) extends profile.api.Table[ContentcontextRow](_tableTag, "ContentContext") {
    def * = (contentcontextid, context) <> (ContentcontextRow.tupled, ContentcontextRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(contentcontextid), Rep.Some(context))).shaped.<>({r=>import r._; _1.map(_=> ContentcontextRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ContentContextId SqlType(INTEGER), AutoInc, PrimaryKey */
    val contentcontextid: Rep[Int] = column[Int]("ContentContextId", O.AutoInc, O.PrimaryKey)
    /** Database column Context SqlType(TEXT) */
    val context: Rep[String] = column[String]("Context")
  }
  /** Collection-like TableQuery object for table Contentcontext */
  lazy val Contentcontext = new TableQuery(tag => new Contentcontext(tag))

  /** Entity class storing rows of table Demandsideplatform
   *  @param demandsideplatformid Database column DemandSidePlatformId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param demandsideplatformname Database column DemandSidePlatformName SqlType(TEXT) */
  case class DemandsideplatformRow(demandsideplatformid: Int, demandsideplatformname: String)
  /** GetResult implicit for fetching DemandsideplatformRow objects using plain SQL queries */
  implicit def GetResultDemandsideplatformRow(implicit e0: GR[Int], e1: GR[String]): GR[DemandsideplatformRow] = GR{
    prs => import prs._
    DemandsideplatformRow.tupled((<<[Int], <<[String]))
  }
  /** Table description of table DemandSidePlatform. Objects of this class serve as prototypes for rows in queries. */
  class Demandsideplatform(_tableTag: Tag) extends profile.api.Table[DemandsideplatformRow](_tableTag, "DemandSidePlatform") {
    def * = (demandsideplatformid, demandsideplatformname) <> (DemandsideplatformRow.tupled, DemandsideplatformRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(demandsideplatformid), Rep.Some(demandsideplatformname))).shaped.<>({r=>import r._; _1.map(_=> DemandsideplatformRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column DemandSidePlatformId SqlType(INTEGER), AutoInc, PrimaryKey */
    val demandsideplatformid: Rep[Int] = column[Int]("DemandSidePlatformId", O.AutoInc, O.PrimaryKey)
    /** Database column DemandSidePlatformName SqlType(TEXT) */
    val demandsideplatformname: Rep[String] = column[String]("DemandSidePlatformName")
  }
  /** Collection-like TableQuery object for table Demandsideplatform */
  lazy val Demandsideplatform = new TableQuery(tag => new Demandsideplatform(tag))

  /** Entity class storing rows of table Geomapping
   *  @param geomappingid Database column GeoMappingId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param advertiseid Database column AdvertiseId SqlType(INTEGER)
   *  @param country Database column Country SqlType(TEXT)
   *  @param hascity Database column HasCity SqlType(INTEGER), Length(1,false), Default(0)
   *  @param city Database column City SqlType(TEXT), Default(None) */
  case class GeomappingRow(geomappingid: Int, advertiseid: Int, country: String, hascity: Int = 0, city: Option[String] = None)
  /** GetResult implicit for fetching GeomappingRow objects using plain SQL queries */
  implicit def GetResultGeomappingRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]]): GR[GeomappingRow] = GR{
    prs => import prs._
    GeomappingRow.tupled((<<[Int], <<[Int], <<[String], <<[Int], <<?[String]))
  }
  /** Table description of table GeoMapping. Objects of this class serve as prototypes for rows in queries. */
  class Geomapping(_tableTag: Tag) extends profile.api.Table[GeomappingRow](_tableTag, "GeoMapping") {
    def * = (geomappingid, advertiseid, country, hascity, city) <> (GeomappingRow.tupled, GeomappingRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(geomappingid), Rep.Some(advertiseid), Rep.Some(country), Rep.Some(hascity), city)).shaped.<>({r=>import r._; _1.map(_=> GeomappingRow.tupled((_1.get, _2.get, _3.get, _4.get, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column GeoMappingId SqlType(INTEGER), AutoInc, PrimaryKey */
    val geomappingid: Rep[Int] = column[Int]("GeoMappingId", O.AutoInc, O.PrimaryKey)
    /** Database column AdvertiseId SqlType(INTEGER) */
    val advertiseid: Rep[Int] = column[Int]("AdvertiseId")
    /** Database column Country SqlType(TEXT) */
    val country: Rep[String] = column[String]("Country")
    /** Database column HasCity SqlType(INTEGER), Length(1,false), Default(0) */
    val hascity: Rep[Int] = column[Int]("HasCity", O.Length(1,varying=false), O.Default(0))
    /** Database column City SqlType(TEXT), Default(None) */
    val city: Rep[Option[String]] = column[Option[String]]("City", O.Default(None))

    /** Foreign key referencing Advertise (database name Advertise_FK_1) */
    lazy val advertiseFk = foreignKey("Advertise_FK_1", advertiseid, Advertise)(r => r.advertiseid, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Geomapping */
  lazy val Geomapping = new TableQuery(tag => new Geomapping(tag))

  /** Entity class storing rows of table Impression
   *  @param impressionid Database column ImpressionID SqlType(TEXT)
   *  @param advertiseid Database column AdvertiseID SqlType(INTEGER)
   *  @param dated Database column Dated SqlType(INTEGER)
   *  @param bidid Database column BidID SqlType(INTEGER)
   *  @param price Database column Price SqlType(REAL)
   *  @param currency Database column Currency SqlType(TEXT) */
  case class ImpressionRow(impressionid: String, advertiseid: Int, dated: Int, bidid: Int, price: Double, currency: String)
  /** GetResult implicit for fetching ImpressionRow objects using plain SQL queries */
  implicit def GetResultImpressionRow(implicit e0: GR[String], e1: GR[Int], e2: GR[Double]): GR[ImpressionRow] = GR{
    prs => import prs._
    ImpressionRow.tupled((<<[String], <<[Int], <<[Int], <<[Int], <<[Double], <<[String]))
  }
  /** Table description of table Impression. Objects of this class serve as prototypes for rows in queries. */
  class Impression(_tableTag: Tag) extends profile.api.Table[ImpressionRow](_tableTag, "Impression") {
    def * = (impressionid, advertiseid, dated, bidid, price, currency) <> (ImpressionRow.tupled, ImpressionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(impressionid), Rep.Some(advertiseid), Rep.Some(dated), Rep.Some(bidid), Rep.Some(price), Rep.Some(currency))).shaped.<>({r=>import r._; _1.map(_=> ImpressionRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ImpressionID SqlType(TEXT) */
    val impressionid: Rep[String] = column[String]("ImpressionID")
    /** Database column AdvertiseID SqlType(INTEGER) */
    val advertiseid: Rep[Int] = column[Int]("AdvertiseID")
    /** Database column Dated SqlType(INTEGER) */
    val dated: Rep[Int] = column[Int]("Dated")
    /** Database column BidID SqlType(INTEGER) */
    val bidid: Rep[Int] = column[Int]("BidID")
    /** Database column Price SqlType(REAL) */
    val price: Rep[Double] = column[Double]("Price")
    /** Database column Currency SqlType(TEXT) */
    val currency: Rep[String] = column[String]("Currency")

    /** Foreign key referencing Advertise (database name Advertise_FK_1) */
    lazy val advertiseFk = foreignKey("Advertise_FK_1", advertiseid, Advertise)(r => r.advertiseid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Impression */
  lazy val Impression = new TableQuery(tag => new Impression(tag))

  /** Entity class storing rows of table Nobidresponse
   *  @param nobidresponseid Database column NoBidResponseId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param bidrequestid Database column BidRequestID SqlType(INTEGER)
   *  @param nobidresponsetypeid Database column NoBidResponseTypeId SqlType(INTEGER) */
  case class NobidresponseRow(nobidresponseid: Int, bidrequestid: Option[Int], nobidresponsetypeid: Option[Int])
  /** GetResult implicit for fetching NobidresponseRow objects using plain SQL queries */
  implicit def GetResultNobidresponseRow(implicit e0: GR[Int], e1: GR[Option[Int]]): GR[NobidresponseRow] = GR{
    prs => import prs._
    NobidresponseRow.tupled((<<[Int], <<?[Int], <<?[Int]))
  }
  /** Table description of table NoBidResponse. Objects of this class serve as prototypes for rows in queries. */
  class Nobidresponse(_tableTag: Tag) extends profile.api.Table[NobidresponseRow](_tableTag, "NoBidResponse") {
    def * = (nobidresponseid, bidrequestid, nobidresponsetypeid) <> (NobidresponseRow.tupled, NobidresponseRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(nobidresponseid), bidrequestid, nobidresponsetypeid)).shaped.<>({r=>import r._; _1.map(_=> NobidresponseRow.tupled((_1.get, _2, _3)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column NoBidResponseId SqlType(INTEGER), AutoInc, PrimaryKey */
    val nobidresponseid: Rep[Int] = column[Int]("NoBidResponseId", O.AutoInc, O.PrimaryKey)
    /** Database column BidRequestID SqlType(INTEGER) */
    val bidrequestid: Rep[Option[Int]] = column[Option[Int]]("BidRequestID")
    /** Database column NoBidResponseTypeId SqlType(INTEGER) */
    val nobidresponsetypeid: Rep[Option[Int]] = column[Option[Int]]("NoBidResponseTypeId")

    /** Foreign key referencing Bidrequest (database name BidRequest_FK_1) */
    lazy val bidrequestFk = foreignKey("BidRequest_FK_1", bidrequestid, Bidrequest)(r => Rep.Some(r.bidrequestid), onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
    /** Foreign key referencing Nobidresponsetype (database name NoBidResponseType_FK_2) */
    lazy val nobidresponsetypeFk = foreignKey("NoBidResponseType_FK_2", nobidresponsetypeid, Nobidresponsetype)(r => Rep.Some(r.nobidresponsetypeid), onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Nobidresponse */
  lazy val Nobidresponse = new TableQuery(tag => new Nobidresponse(tag))

  /** Entity class storing rows of table Nobidresponsetype
   *  @param nobidresponsetypeid Database column NoBidResponseTypeId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param nobidresponsetype Database column NoBidResponseType SqlType(TEXT) */
  case class NobidresponsetypeRow(nobidresponsetypeid: Int, nobidresponsetype: Option[String])
  /** GetResult implicit for fetching NobidresponsetypeRow objects using plain SQL queries */
  implicit def GetResultNobidresponsetypeRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[NobidresponsetypeRow] = GR{
    prs => import prs._
    NobidresponsetypeRow.tupled((<<[Int], <<?[String]))
  }
  /** Table description of table NoBidResponseType. Objects of this class serve as prototypes for rows in queries. */
  class Nobidresponsetype(_tableTag: Tag) extends profile.api.Table[NobidresponsetypeRow](_tableTag, "NoBidResponseType") {
    def * = (nobidresponsetypeid, nobidresponsetype) <> (NobidresponsetypeRow.tupled, NobidresponsetypeRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(nobidresponsetypeid), nobidresponsetype)).shaped.<>({r=>import r._; _1.map(_=> NobidresponsetypeRow.tupled((_1.get, _2)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column NoBidResponseTypeId SqlType(INTEGER), AutoInc, PrimaryKey */
    val nobidresponsetypeid: Rep[Int] = column[Int]("NoBidResponseTypeId", O.AutoInc, O.PrimaryKey)
    /** Database column NoBidResponseType SqlType(TEXT) */
    val nobidresponsetype: Rep[Option[String]] = column[Option[String]]("NoBidResponseType")
  }
  /** Collection-like TableQuery object for table Nobidresponsetype */
  lazy val Nobidresponsetype = new TableQuery(tag => new Nobidresponsetype(tag))

  /** Entity class storing rows of table Transaction
   *  @param transactionid Database column TransactionId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param advertiseid Database column AdvertiseID SqlType(INTEGER)
   *  @param spend Database column Spend SqlType(REAL)
   *  @param impressionid Database column ImpressionID SqlType(INTEGER)
   *  @param currency Database column Currency SqlType(TEXT) */
  case class TransactionRow(transactionid: Int, advertiseid: Int, spend: Double, impressionid: Int, currency: String)
  /** GetResult implicit for fetching TransactionRow objects using plain SQL queries */
  implicit def GetResultTransactionRow(implicit e0: GR[Int], e1: GR[Double], e2: GR[String]): GR[TransactionRow] = GR{
    prs => import prs._
    TransactionRow.tupled((<<[Int], <<[Int], <<[Double], <<[Int], <<[String]))
  }
  /** Table description of table Transaction. Objects of this class serve as prototypes for rows in queries. */
  class Transaction(_tableTag: Tag) extends profile.api.Table[TransactionRow](_tableTag, "Transaction") {
    def * = (transactionid, advertiseid, spend, impressionid, currency) <> (TransactionRow.tupled, TransactionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(transactionid), Rep.Some(advertiseid), Rep.Some(spend), Rep.Some(impressionid), Rep.Some(currency))).shaped.<>({r=>import r._; _1.map(_=> TransactionRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column TransactionId SqlType(INTEGER), AutoInc, PrimaryKey */
    val transactionid: Rep[Int] = column[Int]("TransactionId", O.AutoInc, O.PrimaryKey)
    /** Database column AdvertiseID SqlType(INTEGER) */
    val advertiseid: Rep[Int] = column[Int]("AdvertiseID")
    /** Database column Spend SqlType(REAL) */
    val spend: Rep[Double] = column[Double]("Spend")
    /** Database column ImpressionID SqlType(INTEGER) */
    val impressionid: Rep[Int] = column[Int]("ImpressionID")
    /** Database column Currency SqlType(TEXT) */
    val currency: Rep[String] = column[String]("Currency")

    /** Foreign key referencing Advertise (database name Advertise_FK_1) */
    lazy val advertiseFk = foreignKey("Advertise_FK_1", advertiseid, Advertise)(r => r.advertiseid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Impression (database name Impression_FK_2) */
    lazy val impressionFk = foreignKey("Impression_FK_2", impressionid, Impression)(r => r.impressionid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Transaction */
  lazy val Transaction = new TableQuery(tag => new Transaction(tag))
}
