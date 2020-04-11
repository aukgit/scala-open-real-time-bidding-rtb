package com.ortb.persistent.schema
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
  lazy val schema: profile.SchemaDescription = Array(Advertise.schema, Auction.schema, Banneradvertisetype.schema, Bidrequest.schema, Bidresponse.schema, Campaign.schema, Campaigntargetcity.schema, Campaigntargetsite.schema, Contentcategory.schema, Contentcontext.schema, Demandsideplatform.schema, Geomapping.schema, Impression.schema, Keyword.schema, Keywordadvertisemapping.schema, Lostbid.schema, Nobidresponsetype.schema, Publisher.schema, Transaction.schema).reduceLeft(_ ++ _)
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Advertise
   *  @param advertiseid Database column AdvertiseId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param campaignid Database column CampaignId SqlType(INTEGER)
   *  @param banneradvertisetypeid Database column BannerAdvertiseTypeId SqlType(INTEGER)
   *  @param advertisetitle Database column AdvertiseTitle SqlType(TEXT)
   *  @param contentcontextid Database column ContentContextId SqlType(INTEGER)
   *  @param bidurl Database column BidUrl SqlType(TEXT)
   *  @param iframehtml Database column IFrameHtml SqlType(TEXT)
   *  @param iscountryspecific Database column IsCountrySpecific SqlType(INTEGER), Length(1,false), Default(0)
   *  @param isvideo Database column IsVideo SqlType(INTEGER), Length(1,false), Default(0)
   *  @param impressioncount Database column ImpressionCount SqlType(INTEGER), Default(0)
   *  @param height Database column Height SqlType(REAL), Default(0.0)
   *  @param weight Database column Weight SqlType(REAL), Default(0.0)
   *  @param minheight Database column MinHeight SqlType(REAL), Default(0.0)
   *  @param minweight Database column MinWeight SqlType(REAL), Default(0.0)
   *  @param maxheight Database column MaxHeight SqlType(REAL), Default(0.0)
   *  @param maxweight Database column MaxWeight SqlType(REAL), Default(0.0)
   *  @param hasagerestriction Database column HasAgeRestriction SqlType(INTEGER), Length(1,false)
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

    /** Database column AdvertiseId SqlType(INTEGER), AutoInc, PrimaryKey */
    val advertiseid: Rep[Int] = column[Int]("AdvertiseId", O.AutoInc, O.PrimaryKey)
    /** Database column CampaignId SqlType(INTEGER) */
    val campaignid: Rep[Int] = column[Int]("CampaignId")
    /** Database column BannerAdvertiseTypeId SqlType(INTEGER) */
    val banneradvertisetypeid: Rep[Int] = column[Int]("BannerAdvertiseTypeId")
    /** Database column AdvertiseTitle SqlType(TEXT) */
    val advertisetitle: Rep[String] = column[String]("AdvertiseTitle")
    /** Database column ContentContextId SqlType(INTEGER) */
    val contentcontextid: Rep[Int] = column[Int]("ContentContextId")
    /** Database column BidUrl SqlType(TEXT) */
    val bidurl: Rep[String] = column[String]("BidUrl")
    /** Database column IFrameHtml SqlType(TEXT) */
    val iframehtml: Rep[Option[String]] = column[Option[String]]("IFrameHtml")
    /** Database column IsCountrySpecific SqlType(INTEGER), Length(1,false), Default(0) */
    val iscountryspecific: Rep[Int] = column[Int]("IsCountrySpecific", O.Length(1,varying=false), O.Default(0))
    /** Database column IsVideo SqlType(INTEGER), Length(1,false), Default(0) */
    val isvideo: Rep[Int] = column[Int]("IsVideo", O.Length(1,varying=false), O.Default(0))
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
    /** Database column HasAgeRestriction SqlType(INTEGER), Length(1,false) */
    val hasagerestriction: Rep[Int] = column[Int]("HasAgeRestriction", O.Length(1,varying=false))
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

  /** Entity class storing rows of table Auction
   *  @param auctionid Database column AuctionId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param advertiseid Database column AdvertiseId SqlType(INTEGER)
   *  @param dated Database column Dated SqlType(REAL)
   *  @param winningbidderid Database column WinningBidderId SqlType(INTEGER)
   *  @param isprecachedadvertiseserved Database column IsPrecachedAdvertiseServed SqlType(INTEGER), Length(1,false), Default(0)
   *  @param winningprice Database column WinningPrice SqlType(REAL), Default(-1.0)
   *  @param currency Database column Currency SqlType(TEXT) */
  case class AuctionRow(auctionid: Int, advertiseid: Int, dated: Double, winningbidderid: Option[Int], isprecachedadvertiseserved: Int = 0, winningprice: Double = -1.0, currency: String)
  /** GetResult implicit for fetching AuctionRow objects using plain SQL queries */
  implicit def GetResultAuctionRow(implicit e0: GR[Int], e1: GR[Double], e2: GR[Option[Int]], e3: GR[String]): GR[AuctionRow] = GR{
    prs => import prs._
    AuctionRow.tupled((<<[Int], <<[Int], <<[Double], <<?[Int], <<[Int], <<[Double], <<[String]))
  }
  /** Table description of table Auction. Objects of this class serve as prototypes for rows in queries. */
  class Auction(_tableTag: Tag) extends profile.api.Table[AuctionRow](_tableTag, "Auction") {
    def * = (auctionid, advertiseid, dated, winningbidderid, isprecachedadvertiseserved, winningprice, currency) <> (AuctionRow.tupled, AuctionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(auctionid), Rep.Some(advertiseid), Rep.Some(dated), winningbidderid, Rep.Some(isprecachedadvertiseserved), Rep.Some(winningprice), Rep.Some(currency))).shaped.<>({r=>import r._; _1.map(_=> AuctionRow.tupled((_1.get, _2.get, _3.get, _4, _5.get, _6.get, _7.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column AuctionId SqlType(INTEGER), AutoInc, PrimaryKey */
    val auctionid: Rep[Int] = column[Int]("AuctionId", O.AutoInc, O.PrimaryKey)
    /** Database column AdvertiseId SqlType(INTEGER) */
    val advertiseid: Rep[Int] = column[Int]("AdvertiseId")
    /** Database column Dated SqlType(REAL) */
    val dated: Rep[Double] = column[Double]("Dated")
    /** Database column WinningBidderId SqlType(INTEGER) */
    val winningbidderid: Rep[Option[Int]] = column[Option[Int]]("WinningBidderId")
    /** Database column IsPrecachedAdvertiseServed SqlType(INTEGER), Length(1,false), Default(0) */
    val isprecachedadvertiseserved: Rep[Int] = column[Int]("IsPrecachedAdvertiseServed", O.Length(1,varying=false), O.Default(0))
    /** Database column WinningPrice SqlType(REAL), Default(-1.0) */
    val winningprice: Rep[Double] = column[Double]("WinningPrice", O.Default(-1.0))
    /** Database column Currency SqlType(TEXT) */
    val currency: Rep[String] = column[String]("Currency")

    /** Foreign key referencing Advertise (database name Advertise_FK_1) */
    lazy val advertiseFk = foreignKey("Advertise_FK_1", advertiseid, Advertise)(r => r.advertiseid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Bidrequest (database name BidRequest_FK_2) */
    lazy val bidrequestFk = foreignKey("BidRequest_FK_2", winningbidderid, Bidrequest)(r => Rep.Some(r.bidrequestid), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Auction */
  lazy val Auction = new TableQuery(tag => new Auction(tag))

  /** Entity class storing rows of table Banneradvertisetype
   *  @param banneradvertisetypeid Database column BannerAdvertiseTypeId SqlType(INTEGER), AutoInc, PrimaryKey
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

    /** Database column BannerAdvertiseTypeId SqlType(INTEGER), AutoInc, PrimaryKey */
    val banneradvertisetypeid: Rep[Int] = column[Int]("BannerAdvertiseTypeId", O.AutoInc, O.PrimaryKey)
    /** Database column TypeName SqlType(TEXT) */
    val typename: Rep[String] = column[String]("TypeName")
  }
  /** Collection-like TableQuery object for table Banneradvertisetype */
  lazy val Banneradvertisetype = new TableQuery(tag => new Banneradvertisetype(tag))

  /** Entity class storing rows of table Bidrequest
   *  @param bidrequestid Database column BidRequestId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param demandsideplatformid Database column DemandSidePlatformId SqlType(INTEGER)
   *  @param auctionid Database column AuctionId SqlType(INTEGER)
   *  @param isbanner Database column IsBanner SqlType(INTEGER), Length(1,false), Default(0)
   *  @param isvideo Database column IsVideo SqlType(INTEGER), Length(1,false), Default(0)
   *  @param height Database column Height SqlType(INTEGER)
   *  @param width Database column Width SqlType(INTEGER)
   *  @param countries Database column Countries SqlType(TEXT)
   *  @param cities Database column Cities SqlType(TEXT)
   *  @param targetedsites Database column TargetedSites SqlType(TEXT)
   *  @param targetedcities Database column TargetedCities SqlType(TEXT)
   *  @param rawbidrequest Database column RawBidRequest SqlType(TEXT)
   *  @param price1 Database column Price1 SqlType(REAL), Default(Some(0.0))
   *  @param price2 Database column Price2 SqlType(REAL), Default(Some(0.0))
   *  @param price3 Database column Price3 SqlType(REAL), Default(Some(0.0))
   *  @param currency Database column Currency SqlType(TEXT), Length(5,false)
   *  @param contentcontextid Database column ContentContextId SqlType(INTEGER)
   *  @param iswontheauction Database column IsWonTheAuction SqlType(INTEGER), Length(1,false), Default(0) */
  case class BidrequestRow(bidrequestid: Int, demandsideplatformid: Int, auctionid: Option[Int], isbanner: Int = 0, isvideo: Int = 0, height: Option[Int], width: Option[Int], countries: Option[String], cities: Option[String], targetedsites: Option[String], targetedcities: Option[String], rawbidrequest: Option[String], price1: Option[Double] = Some(0.0), price2: Option[Double] = Some(0.0), price3: Option[Double] = Some(0.0), currency: Option[String], contentcontextid: Option[Int], iswontheauction: Int = 0)
  /** GetResult implicit for fetching BidrequestRow objects using plain SQL queries */
  implicit def GetResultBidrequestRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[Option[String]], e3: GR[Option[Double]]): GR[BidrequestRow] = GR{
    prs => import prs._
    BidrequestRow.tupled((<<[Int], <<[Int], <<?[Int], <<[Int], <<[Int], <<?[Int], <<?[Int], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[Double], <<?[Double], <<?[Double], <<?[String], <<?[Int], <<[Int]))
  }
  /** Table description of table BidRequest. Objects of this class serve as prototypes for rows in queries. */
  class Bidrequest(_tableTag: Tag) extends profile.api.Table[BidrequestRow](_tableTag, "BidRequest") {
    def * = (bidrequestid, demandsideplatformid, auctionid, isbanner, isvideo, height, width, countries, cities, targetedsites, targetedcities, rawbidrequest, price1, price2, price3, currency, contentcontextid, iswontheauction) <> (BidrequestRow.tupled, BidrequestRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(bidrequestid), Rep.Some(demandsideplatformid), auctionid, Rep.Some(isbanner), Rep.Some(isvideo), height, width, countries, cities, targetedsites, targetedcities, rawbidrequest, price1, price2, price3, currency, contentcontextid, Rep.Some(iswontheauction))).shaped.<>({r=>import r._; _1.map(_=> BidrequestRow.tupled((_1.get, _2.get, _3, _4.get, _5.get, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16, _17, _18.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column BidRequestId SqlType(INTEGER), AutoInc, PrimaryKey */
    val bidrequestid: Rep[Int] = column[Int]("BidRequestId", O.AutoInc, O.PrimaryKey)
    /** Database column DemandSidePlatformId SqlType(INTEGER) */
    val demandsideplatformid: Rep[Int] = column[Int]("DemandSidePlatformId")
    /** Database column AuctionId SqlType(INTEGER) */
    val auctionid: Rep[Option[Int]] = column[Option[Int]]("AuctionId")
    /** Database column IsBanner SqlType(INTEGER), Length(1,false), Default(0) */
    val isbanner: Rep[Int] = column[Int]("IsBanner", O.Length(1,varying=false), O.Default(0))
    /** Database column IsVideo SqlType(INTEGER), Length(1,false), Default(0) */
    val isvideo: Rep[Int] = column[Int]("IsVideo", O.Length(1,varying=false), O.Default(0))
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
    /** Database column Price1 SqlType(REAL), Default(Some(0.0)) */
    val price1: Rep[Option[Double]] = column[Option[Double]]("Price1", O.Default(Some(0.0)))
    /** Database column Price2 SqlType(REAL), Default(Some(0.0)) */
    val price2: Rep[Option[Double]] = column[Option[Double]]("Price2", O.Default(Some(0.0)))
    /** Database column Price3 SqlType(REAL), Default(Some(0.0)) */
    val price3: Rep[Option[Double]] = column[Option[Double]]("Price3", O.Default(Some(0.0)))
    /** Database column Currency SqlType(TEXT), Length(5,false) */
    val currency: Rep[Option[String]] = column[Option[String]]("Currency", O.Length(5,varying=false))
    /** Database column ContentContextId SqlType(INTEGER) */
    val contentcontextid: Rep[Option[Int]] = column[Option[Int]]("ContentContextId")
    /** Database column IsWonTheAuction SqlType(INTEGER), Length(1,false), Default(0) */
    val iswontheauction: Rep[Int] = column[Int]("IsWonTheAuction", O.Length(1,varying=false), O.Default(0))

    /** Foreign key referencing Auction (database name Auction_FK_1) */
    lazy val auctionFk = foreignKey("Auction_FK_1", auctionid, Auction)(r => Rep.Some(r.auctionid), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Contentcontext (database name ContentContext_FK_2) */
    lazy val contentcontextFk = foreignKey("ContentContext_FK_2", contentcontextid, Contentcontext)(r => Rep.Some(r.contentcontextid), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Demandsideplatform (database name DemandSidePlatform_FK_3) */
    lazy val demandsideplatformFk = foreignKey("DemandSidePlatform_FK_3", demandsideplatformid, Demandsideplatform)(r => r.demandsideplatformid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Bidrequest */
  lazy val Bidrequest = new TableQuery(tag => new Bidrequest(tag))

  /** Entity class storing rows of table Bidresponse
   *  @param bidresponseid Database column BidResponseId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param price Database column Price SqlType(REAL)
   *  @param currency Database column Currency SqlType(TEXT)
   *  @param adm Database column Adm SqlType(TEXT)
   *  @param nurl Database column NUrl SqlType(TEXT)
   *  @param iurl Database column IUrl SqlType(TEXT)
   *  @param advertiseid Database column AdvertiseId SqlType(INTEGER)
   *  @param bidrequestid Database column BidRequestId SqlType(INTEGER)
   *  @param iswontheauction Database column IsWonTheAuction SqlType(INTEGER), Length(1,false), Default(0)
   *  @param isauctionoccured Database column IsAuctionOccured SqlType(INTEGER), Length(1,false), Default(0)
   *  @param isprecachedbidserved Database column IsPreCachedBidServed SqlType(INTEGER), Length(1,false), Default(0)
   *  @param issendnobidresponse Database column IsSendNoBidResponse SqlType(INTEGER), Length(1,false), Default(0)
   *  @param nobidresponsetypeid Database column NoBidResponseTypeId SqlType(INTEGER) */
  case class BidresponseRow(bidresponseid: Int, price: Double, currency: String, adm: Option[String], nurl: String, iurl: Option[String], advertiseid: Int, bidrequestid: Option[Int], iswontheauction: Int = 0, isauctionoccured: Int = 0, isprecachedbidserved: Int = 0, issendnobidresponse: Int = 0, nobidresponsetypeid: Option[Int])
  /** GetResult implicit for fetching BidresponseRow objects using plain SQL queries */
  implicit def GetResultBidresponseRow(implicit e0: GR[Int], e1: GR[Double], e2: GR[String], e3: GR[Option[String]], e4: GR[Option[Int]]): GR[BidresponseRow] = GR{
    prs => import prs._
    BidresponseRow.tupled((<<[Int], <<[Double], <<[String], <<?[String], <<[String], <<?[String], <<[Int], <<?[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<?[Int]))
  }
  /** Table description of table BidResponse. Objects of this class serve as prototypes for rows in queries. */
  class Bidresponse(_tableTag: Tag) extends profile.api.Table[BidresponseRow](_tableTag, "BidResponse") {
    def * = (bidresponseid, price, currency, adm, nurl, iurl, advertiseid, bidrequestid, iswontheauction, isauctionoccured, isprecachedbidserved, issendnobidresponse, nobidresponsetypeid) <> (BidresponseRow.tupled, BidresponseRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(bidresponseid), Rep.Some(price), Rep.Some(currency), adm, Rep.Some(nurl), iurl, Rep.Some(advertiseid), bidrequestid, Rep.Some(iswontheauction), Rep.Some(isauctionoccured), Rep.Some(isprecachedbidserved), Rep.Some(issendnobidresponse), nobidresponsetypeid)).shaped.<>({r=>import r._; _1.map(_=> BidresponseRow.tupled((_1.get, _2.get, _3.get, _4, _5.get, _6, _7.get, _8, _9.get, _10.get, _11.get, _12.get, _13)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column BidResponseId SqlType(INTEGER), AutoInc, PrimaryKey */
    val bidresponseid: Rep[Int] = column[Int]("BidResponseId", O.AutoInc, O.PrimaryKey)
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
    /** Database column AdvertiseId SqlType(INTEGER) */
    val advertiseid: Rep[Int] = column[Int]("AdvertiseId")
    /** Database column BidRequestId SqlType(INTEGER) */
    val bidrequestid: Rep[Option[Int]] = column[Option[Int]]("BidRequestId")
    /** Database column IsWonTheAuction SqlType(INTEGER), Length(1,false), Default(0) */
    val iswontheauction: Rep[Int] = column[Int]("IsWonTheAuction", O.Length(1,varying=false), O.Default(0))
    /** Database column IsAuctionOccured SqlType(INTEGER), Length(1,false), Default(0) */
    val isauctionoccured: Rep[Int] = column[Int]("IsAuctionOccured", O.Length(1,varying=false), O.Default(0))
    /** Database column IsPreCachedBidServed SqlType(INTEGER), Length(1,false), Default(0) */
    val isprecachedbidserved: Rep[Int] = column[Int]("IsPreCachedBidServed", O.Length(1,varying=false), O.Default(0))
    /** Database column IsSendNoBidResponse SqlType(INTEGER), Length(1,false), Default(0) */
    val issendnobidresponse: Rep[Int] = column[Int]("IsSendNoBidResponse", O.Length(1,varying=false), O.Default(0))
    /** Database column NoBidResponseTypeId SqlType(INTEGER) */
    val nobidresponsetypeid: Rep[Option[Int]] = column[Option[Int]]("NoBidResponseTypeId")

    /** Foreign key referencing Advertise (database name Advertise_FK_1) */
    lazy val advertiseFk = foreignKey("Advertise_FK_1", advertiseid, Advertise)(r => r.advertiseid, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
    /** Foreign key referencing Bidrequest (database name BidRequest_FK_2) */
    lazy val bidrequestFk = foreignKey("BidRequest_FK_2", bidrequestid, Bidrequest)(r => Rep.Some(r.bidrequestid), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Nobidresponsetype (database name NoBidResponseType_FK_3) */
    lazy val nobidresponsetypeFk = foreignKey("NoBidResponseType_FK_3", nobidresponsetypeid, Nobidresponsetype)(r => Rep.Some(r.nobidresponsetypeid), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Bidresponse */
  lazy val Bidresponse = new TableQuery(tag => new Bidresponse(tag))

  /** Entity class storing rows of table Campaign
   *  @param campaignid Database column CampaignId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param campaignname Database column CampaignName SqlType(TEXT)
   *  @param contentcategoryid Database column ContentCategoryId SqlType(TEXT)
   *  @param totalbudgetcpm Database column TotalBudgetCPM SqlType(REAL), Default(0.0)
   *  @param spendalready Database column SpendAlready SqlType(REAL), Default(0.0)
   *  @param remainingamount Database column RemainingAmount SqlType(REAL), Default(0.0)
   *  @param startdate Database column StartDate SqlType(REAL), Default(Some(0.0))
   *  @param enddate Database column EndDate SqlType(REAL), Default(Some(0.0))
   *  @param impressioncount Database column ImpressionCount SqlType(INTEGER), Default(0)
   *  @param demandsideplatformid Database column DemandSidePlatformId SqlType(INTEGER)
   *  @param isrunning Database column IsRunning SqlType(INTEGER), Length(1,false), Default(0)
   *  @param priority Database column Priority SqlType(INTEGER), Length(3,false), Default(999)
   *  @param isretricttousergender Database column IsRetrictToUserGender SqlType(INTEGER), Length(1,false), Default(0)
   *  @param expectedusergender Database column ExpectedUserGender SqlType(TEXT), Length(2,false)
   *  @param publisherid Database column PublisherId SqlType(INTEGER) */
  case class CampaignRow(campaignid: Int, campaignname: String, contentcategoryid: String, totalbudgetcpm: Double = 0.0, spendalready: Double = 0.0, remainingamount: Double = 0.0, startdate: Option[Double] = Some(0.0), enddate: Option[Double] = Some(0.0), impressioncount: Int = 0, demandsideplatformid: Int, isrunning: Int = 0, priority: Int = 999, isretricttousergender: Int = 0, expectedusergender: Option[String], publisherid: Option[Int])
  /** GetResult implicit for fetching CampaignRow objects using plain SQL queries */
  implicit def GetResultCampaignRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Double], e3: GR[Option[Double]], e4: GR[Option[String]], e5: GR[Option[Int]]): GR[CampaignRow] = GR{
    prs => import prs._
    CampaignRow.tupled((<<[Int], <<[String], <<[String], <<[Double], <<[Double], <<[Double], <<?[Double], <<?[Double], <<[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<?[String], <<?[Int]))
  }
  /** Table description of table Campaign. Objects of this class serve as prototypes for rows in queries. */
  class Campaign(_tableTag: Tag) extends profile.api.Table[CampaignRow](_tableTag, "Campaign") {
    def * = (campaignid, campaignname, contentcategoryid, totalbudgetcpm, spendalready, remainingamount, startdate, enddate, impressioncount, demandsideplatformid, isrunning, priority, isretricttousergender, expectedusergender, publisherid) <> (CampaignRow.tupled, CampaignRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(campaignid), Rep.Some(campaignname), Rep.Some(contentcategoryid), Rep.Some(totalbudgetcpm), Rep.Some(spendalready), Rep.Some(remainingamount), startdate, enddate, Rep.Some(impressioncount), Rep.Some(demandsideplatformid), Rep.Some(isrunning), Rep.Some(priority), Rep.Some(isretricttousergender), expectedusergender, publisherid)).shaped.<>({r=>import r._; _1.map(_=> CampaignRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7, _8, _9.get, _10.get, _11.get, _12.get, _13.get, _14, _15)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column CampaignId SqlType(INTEGER), AutoInc, PrimaryKey */
    val campaignid: Rep[Int] = column[Int]("CampaignId", O.AutoInc, O.PrimaryKey)
    /** Database column CampaignName SqlType(TEXT) */
    val campaignname: Rep[String] = column[String]("CampaignName")
    /** Database column ContentCategoryId SqlType(TEXT) */
    val contentcategoryid: Rep[String] = column[String]("ContentCategoryId")
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
    /** Database column IsRunning SqlType(INTEGER), Length(1,false), Default(0) */
    val isrunning: Rep[Int] = column[Int]("IsRunning", O.Length(1,varying=false), O.Default(0))
    /** Database column Priority SqlType(INTEGER), Length(3,false), Default(999) */
    val priority: Rep[Int] = column[Int]("Priority", O.Length(3,varying=false), O.Default(999))
    /** Database column IsRetrictToUserGender SqlType(INTEGER), Length(1,false), Default(0) */
    val isretricttousergender: Rep[Int] = column[Int]("IsRetrictToUserGender", O.Length(1,varying=false), O.Default(0))
    /** Database column ExpectedUserGender SqlType(TEXT), Length(2,false) */
    val expectedusergender: Rep[Option[String]] = column[Option[String]]("ExpectedUserGender", O.Length(2,varying=false))
    /** Database column PublisherId SqlType(INTEGER) */
    val publisherid: Rep[Option[Int]] = column[Option[Int]]("PublisherId")

    /** Foreign key referencing Contentcategory (database name ContentCategory_FK_1) */
    lazy val contentcategoryFk = foreignKey("ContentCategory_FK_1", contentcategoryid, Contentcategory)(r => r.contentcategoryid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Demandsideplatform (database name DemandSidePlatform_FK_2) */
    lazy val demandsideplatformFk = foreignKey("DemandSidePlatform_FK_2", demandsideplatformid, Demandsideplatform)(r => r.demandsideplatformid, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
    /** Foreign key referencing Publisher (database name Publisher_FK_3) */
    lazy val publisherFk = foreignKey("Publisher_FK_3", publisherid, Publisher)(r => Rep.Some(r.publisherid), onUpdate=ForeignKeyAction.SetNull, onDelete=ForeignKeyAction.SetNull)
  }
  /** Collection-like TableQuery object for table Campaign */
  lazy val Campaign = new TableQuery(tag => new Campaign(tag))

  /** Entity class storing rows of table Campaigntargetcity
   *  @param campaigntargetcityid Database column CampaignTargetCityId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param campaigntargetcity Database column CampaignTargetCity SqlType(TEXT)
   *  @param campaignid Database column CampaignId SqlType(INTEGER) */
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

    /** Database column CampaignTargetCityId SqlType(INTEGER), AutoInc, PrimaryKey */
    val campaigntargetcityid: Rep[Int] = column[Int]("CampaignTargetCityId", O.AutoInc, O.PrimaryKey)
    /** Database column CampaignTargetCity SqlType(TEXT) */
    val campaigntargetcity: Rep[String] = column[String]("CampaignTargetCity")
    /** Database column CampaignId SqlType(INTEGER) */
    val campaignid: Rep[Int] = column[Int]("CampaignId")

    /** Foreign key referencing Campaign (database name Campaign_FK_1) */
    lazy val campaignFk = foreignKey("Campaign_FK_1", campaignid, Campaign)(r => r.campaignid, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Campaigntargetcity */
  lazy val Campaigntargetcity = new TableQuery(tag => new Campaigntargetcity(tag))

  /** Entity class storing rows of table Campaigntargetsite
   *  @param campaigntargetsiteid Database column CampaignTargetSiteId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param campaigntargetsite Database column CampaignTargetSite SqlType(TEXT)
   *  @param campaignid Database column CampaignId SqlType(INTEGER) */
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

    /** Database column CampaignTargetSiteId SqlType(INTEGER), AutoInc, PrimaryKey */
    val campaigntargetsiteid: Rep[Int] = column[Int]("CampaignTargetSiteId", O.AutoInc, O.PrimaryKey)
    /** Database column CampaignTargetSite SqlType(TEXT) */
    val campaigntargetsite: Rep[String] = column[String]("CampaignTargetSite")
    /** Database column CampaignId SqlType(INTEGER) */
    val campaignid: Rep[Int] = column[Int]("CampaignId")

    /** Foreign key referencing Campaign (database name Campaign_FK_1) */
    lazy val campaignFk = foreignKey("Campaign_FK_1", campaignid, Campaign)(r => r.campaignid, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Campaigntargetsite */
  lazy val Campaigntargetsite = new TableQuery(tag => new Campaigntargetsite(tag))

  /** Entity class storing rows of table Contentcategory
   *  @param contentcategoryid Database column ContentCategoryId SqlType(TEXT), PrimaryKey
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

    /** Database column ContentCategoryId SqlType(TEXT), PrimaryKey */
    val contentcategoryid: Rep[String] = column[String]("ContentCategoryId", O.PrimaryKey)
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
   *  @param impressionid Database column ImpressionId SqlType(TEXT), PrimaryKey
   *  @param advertiseid Database column AdvertiseId SqlType(INTEGER)
   *  @param dated Database column Dated SqlType(INTEGER)
   *  @param bidrequestid Database column BidRequestId SqlType(INTEGER)
   *  @param price Database column Price SqlType(REAL)
   *  @param currency Database column Currency SqlType(TEXT) */
  case class ImpressionRow(impressionid: String, advertiseid: Int, dated: Int, bidrequestid: Int, price: Double, currency: String)
  /** GetResult implicit for fetching ImpressionRow objects using plain SQL queries */
  implicit def GetResultImpressionRow(implicit e0: GR[String], e1: GR[Int], e2: GR[Double]): GR[ImpressionRow] = GR{
    prs => import prs._
    ImpressionRow.tupled((<<[String], <<[Int], <<[Int], <<[Int], <<[Double], <<[String]))
  }
  /** Table description of table Impression. Objects of this class serve as prototypes for rows in queries. */
  class Impression(_tableTag: Tag) extends profile.api.Table[ImpressionRow](_tableTag, "Impression") {
    def * = (impressionid, advertiseid, dated, bidrequestid, price, currency) <> (ImpressionRow.tupled, ImpressionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(impressionid), Rep.Some(advertiseid), Rep.Some(dated), Rep.Some(bidrequestid), Rep.Some(price), Rep.Some(currency))).shaped.<>({r=>import r._; _1.map(_=> ImpressionRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ImpressionId SqlType(TEXT), PrimaryKey */
    val impressionid: Rep[String] = column[String]("ImpressionId", O.PrimaryKey)
    /** Database column AdvertiseId SqlType(INTEGER) */
    val advertiseid: Rep[Int] = column[Int]("AdvertiseId")
    /** Database column Dated SqlType(INTEGER) */
    val dated: Rep[Int] = column[Int]("Dated")
    /** Database column BidRequestId SqlType(INTEGER) */
    val bidrequestid: Rep[Int] = column[Int]("BidRequestId")
    /** Database column Price SqlType(REAL) */
    val price: Rep[Double] = column[Double]("Price")
    /** Database column Currency SqlType(TEXT) */
    val currency: Rep[String] = column[String]("Currency")

    /** Foreign key referencing Advertise (database name Advertise_FK_1) */
    lazy val advertiseFk = foreignKey("Advertise_FK_1", advertiseid, Advertise)(r => r.advertiseid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Impression */
  lazy val Impression = new TableQuery(tag => new Impression(tag))

  /** Entity class storing rows of table Keyword
   *  @param keywordid Database column KeywordId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param keyword Database column Keyword SqlType(TEXT) */
  case class KeywordRow(keywordid: Int, keyword: Option[String])
  /** GetResult implicit for fetching KeywordRow objects using plain SQL queries */
  implicit def GetResultKeywordRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[KeywordRow] = GR{
    prs => import prs._
    KeywordRow.tupled((<<[Int], <<?[String]))
  }
  /** Table description of table Keyword. Objects of this class serve as prototypes for rows in queries. */
  class Keyword(_tableTag: Tag) extends profile.api.Table[KeywordRow](_tableTag, "Keyword") {
    def * = (keywordid, keyword) <> (KeywordRow.tupled, KeywordRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(keywordid), keyword)).shaped.<>({r=>import r._; _1.map(_=> KeywordRow.tupled((_1.get, _2)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column KeywordId SqlType(INTEGER), AutoInc, PrimaryKey */
    val keywordid: Rep[Int] = column[Int]("KeywordId", O.AutoInc, O.PrimaryKey)
    /** Database column Keyword SqlType(TEXT) */
    val keyword: Rep[Option[String]] = column[Option[String]]("Keyword")
  }
  /** Collection-like TableQuery object for table Keyword */
  lazy val Keyword = new TableQuery(tag => new Keyword(tag))

  /** Entity class storing rows of table Keywordadvertisemapping
   *  @param keywordadvertisemappingid Database column KeywordAdvertiseMappingId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param advertiseid Database column AdvertiseId SqlType(INTEGER)
   *  @param keywordid Database column KeywordId SqlType(INTEGER) */
  case class KeywordadvertisemappingRow(keywordadvertisemappingid: Int, advertiseid: Int, keywordid: Int)
  /** GetResult implicit for fetching KeywordadvertisemappingRow objects using plain SQL queries */
  implicit def GetResultKeywordadvertisemappingRow(implicit e0: GR[Int]): GR[KeywordadvertisemappingRow] = GR{
    prs => import prs._
    KeywordadvertisemappingRow.tupled((<<[Int], <<[Int], <<[Int]))
  }
  /** Table description of table KeywordAdvertiseMapping. Objects of this class serve as prototypes for rows in queries. */
  class Keywordadvertisemapping(_tableTag: Tag) extends profile.api.Table[KeywordadvertisemappingRow](_tableTag, "KeywordAdvertiseMapping") {
    def * = (keywordadvertisemappingid, advertiseid, keywordid) <> (KeywordadvertisemappingRow.tupled, KeywordadvertisemappingRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(keywordadvertisemappingid), Rep.Some(advertiseid), Rep.Some(keywordid))).shaped.<>({r=>import r._; _1.map(_=> KeywordadvertisemappingRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column KeywordAdvertiseMappingId SqlType(INTEGER), AutoInc, PrimaryKey */
    val keywordadvertisemappingid: Rep[Int] = column[Int]("KeywordAdvertiseMappingId", O.AutoInc, O.PrimaryKey)
    /** Database column AdvertiseId SqlType(INTEGER) */
    val advertiseid: Rep[Int] = column[Int]("AdvertiseId")
    /** Database column KeywordId SqlType(INTEGER) */
    val keywordid: Rep[Int] = column[Int]("KeywordId")

    /** Foreign key referencing Advertise (database name Advertise_FK_1) */
    lazy val advertiseFk = foreignKey("Advertise_FK_1", advertiseid, Advertise)(r => r.advertiseid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Keyword (database name Keyword_FK_2) */
    lazy val keywordFk = foreignKey("Keyword_FK_2", keywordid, Keyword)(r => r.keywordid, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Keywordadvertisemapping */
  lazy val Keywordadvertisemapping = new TableQuery(tag => new Keywordadvertisemapping(tag))

  /** Entity class storing rows of table Lostbid
   *  @param lostbidid Database column LostBidId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param bidrequestid Database column BidRequestId SqlType(INTEGER)
   *  @param reason Database column Reason SqlType(TEXT) */
  case class LostbidRow(lostbidid: Int, bidrequestid: Int, reason: Option[String])
  /** GetResult implicit for fetching LostbidRow objects using plain SQL queries */
  implicit def GetResultLostbidRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[LostbidRow] = GR{
    prs => import prs._
    LostbidRow.tupled((<<[Int], <<[Int], <<?[String]))
  }
  /** Table description of table LostBid. Objects of this class serve as prototypes for rows in queries. */
  class Lostbid(_tableTag: Tag) extends profile.api.Table[LostbidRow](_tableTag, "LostBid") {
    def * = (lostbidid, bidrequestid, reason) <> (LostbidRow.tupled, LostbidRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(lostbidid), Rep.Some(bidrequestid), reason)).shaped.<>({r=>import r._; _1.map(_=> LostbidRow.tupled((_1.get, _2.get, _3)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column LostBidId SqlType(INTEGER), AutoInc, PrimaryKey */
    val lostbidid: Rep[Int] = column[Int]("LostBidId", O.AutoInc, O.PrimaryKey)
    /** Database column BidRequestId SqlType(INTEGER) */
    val bidrequestid: Rep[Int] = column[Int]("BidRequestId")
    /** Database column Reason SqlType(TEXT) */
    val reason: Rep[Option[String]] = column[Option[String]]("Reason")

    /** Foreign key referencing Bidrequest (database name BidRequest_FK_1) */
    lazy val bidrequestFk = foreignKey("BidRequest_FK_1", bidrequestid, Bidrequest)(r => r.bidrequestid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Lostbid */
  lazy val Lostbid = new TableQuery(tag => new Lostbid(tag))

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

  /** Entity class storing rows of table Publisher
   *  @param publisherid Database column PublisherId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param publishername Database column PublisherName SqlType(TEXT)
   *  @param publisherwebsite Database column PublisherWebsite SqlType(TEXT)
   *  @param publisheraddress Database column PublisherAddress SqlType(TEXT)
   *  @param demandsideplatformid Database column DemandSidePlatformId SqlType(INTEGER) */
  case class PublisherRow(publisherid: Int, publishername: String, publisherwebsite: String, publisheraddress: String, demandsideplatformid: Int)
  /** GetResult implicit for fetching PublisherRow objects using plain SQL queries */
  implicit def GetResultPublisherRow(implicit e0: GR[Int], e1: GR[String]): GR[PublisherRow] = GR{
    prs => import prs._
    PublisherRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[Int]))
  }
  /** Table description of table Publisher. Objects of this class serve as prototypes for rows in queries. */
  class Publisher(_tableTag: Tag) extends profile.api.Table[PublisherRow](_tableTag, "Publisher") {
    def * = (publisherid, publishername, publisherwebsite, publisheraddress, demandsideplatformid) <> (PublisherRow.tupled, PublisherRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(publisherid), Rep.Some(publishername), Rep.Some(publisherwebsite), Rep.Some(publisheraddress), Rep.Some(demandsideplatformid))).shaped.<>({r=>import r._; _1.map(_=> PublisherRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column PublisherId SqlType(INTEGER), AutoInc, PrimaryKey */
    val publisherid: Rep[Int] = column[Int]("PublisherId", O.AutoInc, O.PrimaryKey)
    /** Database column PublisherName SqlType(TEXT) */
    val publishername: Rep[String] = column[String]("PublisherName")
    /** Database column PublisherWebsite SqlType(TEXT) */
    val publisherwebsite: Rep[String] = column[String]("PublisherWebsite")
    /** Database column PublisherAddress SqlType(TEXT) */
    val publisheraddress: Rep[String] = column[String]("PublisherAddress")
    /** Database column DemandSidePlatformId SqlType(INTEGER) */
    val demandsideplatformid: Rep[Int] = column[Int]("DemandSidePlatformId")

    /** Foreign key referencing Demandsideplatform (database name DemandSidePlatform_FK_1) */
    lazy val demandsideplatformFk = foreignKey("DemandSidePlatform_FK_1", demandsideplatformid, Demandsideplatform)(r => r.demandsideplatformid, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Publisher */
  lazy val Publisher = new TableQuery(tag => new Publisher(tag))

  /** Entity class storing rows of table Transaction
   *  @param transactionid Database column TransactionId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param advertiseid Database column AdvertiseId SqlType(INTEGER)
   *  @param spend Database column Spend SqlType(REAL)
   *  @param impressionid Database column ImpressionId SqlType(INTEGER)
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
    /** Database column AdvertiseId SqlType(INTEGER) */
    val advertiseid: Rep[Int] = column[Int]("AdvertiseId")
    /** Database column Spend SqlType(REAL) */
    val spend: Rep[Double] = column[Double]("Spend")
    /** Database column ImpressionId SqlType(INTEGER) */
    val impressionid: Rep[Int] = column[Int]("ImpressionId")
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
